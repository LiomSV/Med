$(document).ready(function() {

    index.makeAutoHight();
    $(window).resize(index.makeAutoHight);

    var mapCanvas = document.getElementById('map');
    var mapOptions = {
        center: new google.maps.LatLng(53.9, 27.5667),
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    index.map = new google.maps.Map(mapCanvas, mapOptions);

    index.update();
    var updateCallsTimer = setInterval(index.update, 4000);

});

var index = {};

(function (index){

    index.crews = [];
    index.calls = [];

    index.callMarkers = {};
    index.crewMarkers = {};
    index.selectedCallId = -1;
    index.selectedCrewId = -1;
    index.hoveredCallId = -1;
    index.hoveredCrewId = -1;
    index.hoveredCallIcon = null;
    index.hoveredCrewIcon = null;

    index.makeAutoHight = function() {
        $('.auto-height').css('height', $(window).height() - 145);
    };

    index.update = function() {
        index.updateCalls();
        index.updateCrews();
    };

    index.updateCalls = function() {
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.call.list,
            method: 'GET',
            success: function(response) {
                var counts = {};
                counts[conf.callType.URGENT] = 0;
                counts[conf.callType.IMMEDIATE] = 0;
                counts[conf.callType.EMERGENT] = 0;
                $('#calls_count').text(response.length);
                $('#calls_table').html(
                    '<tr>' +
                        '<th>№</th>' +
                        '<th>ФИО</th>' +
                        '<th>Адрес</th>' +
                        '<th>Причина</th>' +
                        '<th></th>' +
                    '</tr>');
                response.forEach(function(call) {
                    index.calls[call.id] = call;
                    $('#calls_table').append(
                        '<tr class="call-list-item' + (call.id == index.selectedCallId ? ' selected' : '') + '">' +
                            '<td class="id">' + call.id + '</td>' +
                            '<td>' + (call.lastname || call.firstname || call.fathername) + '</td>' +
                            '<td>' + call.address + '</td>' +
                            '<td>' + call.reason + '</td>' +
                            '<td>' +
                                '<span class="to-details pull-right glyphicon glyphicon-list-alt"></span>' +
                                (call.type == conf.callType.EMERGENT ? '<span class="pull-right glyphicon glyphicon-star" style="margin-right: 7px"></span>' : '') +
                                (call.type == conf.callType.IMMEDIATE ? '<span class="pull-right glyphicon glyphicon-star-empty" style="margin-right: 7px"></span>' : '') +
                            '</td>' +
                        '</tr>');
                    counts[call.type] += 1;
                    if (index.callMarkers[call.id] == null) {
                        index.callMarkers[call.id] = new google.maps.Marker({
                            icon: 'rsc/images/people_24_red.png',
                            map: index.map,
                            position: {
                                lat: call.location.lat,
                                lng: call.location.lng
                            }
                        });
                    }
                });

                var callListItem = $('.call-list-item');
                callListItem.off()
                    .on('click', index.onclickCallList)
                    .on('mouseenter', index.onmouseenterCallList)
                    .on('mouseleave', index.onmouseleaveCallList);

                $('#urgent_count').text(counts[conf.callType.URGENT]);
                $('#immediate_count').text(counts[conf.callType.IMMEDIATE]);
                $('#emergent_count').text(counts[conf.callType.EMERGENT]);
            }
        })
    };

    index.updateCrews = function() {
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.crew.list,
            method: 'GET',
            success: function(response) {
                $('#crews_table').html(
                    '<tr>' +
                        '<th>№</th>' +
                        '<th>Статус</th>' +
                        '<th>Водитель</th>' +
                        '<th>Персонал</th>' +
                        '<th></th>' +
                    '</tr>');
                response.forEach(function(crew) {
                    index.crews[crew.id] = crew;
                    var members = '';
                    crew.members.forEach(function(d) {
                        if (members.length > 0)
                            members += ', ';
                        members += d;
                    });
                    $('#crews_table').append(
                        '<tr class="crew-list-item' + (crew.id == index.selectedCrewId ? ' selected' : '') + '">' +
                            '<td class="id">' + crew.id + '</td>' +
                            '<td>' + main.crewStatusString[crew.status] + '</td>' +
                            '<td>' + crew.driver + '</td>' +
                            '<td>' + members + '</td>' +
                            '<td class="to-details"><span class="glyphicon glyphicon-list-alt"></span></td>' +
                        '</tr>');
                    if (crew.location && index.crewMarkers[crew.id] == null) {
                        index.crewMarkers[crew.id] = new google.maps.Marker({
                            icon: 'rsc/images/transport_24_red.png',
                            map: index.map,
                            position: {
                                lat: crew.location.lat,
                                lng: crew.location.lng
                            }
                        });
                    }
                });

                var crewListItem = $('.crew-list-item');
                crewListItem.off()
                    .on('click', index.onclickCrewList)
                    .on('mouseenter' ,index.onmouseenterCrewList)
                    .on('mouseleave' ,index.onmouseleaveCrewList);
            }
        })
    };

    index.clearMarkerSelection = function() {
        var selectedCallMarker = index.callMarkers[index.selectedCallId];
        if (selectedCallMarker) {
            selectedCallMarker.setIcon('rsc/images/people_24_red.png');
        }
        var selectedCrewMarker = index.crewMarkers[index.selectedCrewId];
        if (selectedCrewMarker) {
            selectedCrewMarker.setIcon('rsc/images/transport_24_red.png');
        }
    };

    index.clearListSelection = function() {
        $('#calls_table').find('.selected').removeClass('selected');
        $('#crews_table').find('.selected').removeClass('selected');
    };

    index.selectCallOnMap = function(callId) {
        console.log(callId);
        console.log(index.selectedCallId);
        if (callId != index.selectedCallId) {
            index.selectedCallId = callId;
            index.callMarkers[callId].setIcon('rsc/images/people_32_black.png');
            index.hoveredCallIcon = index.callMarkers[callId].getIcon();
        } else {
            index.selectedCallId = -1;
            index.hoveredCallIcon = null;
            index.callMarkers[callId].setIcon('rsc/images/people_24_red.png');
        }
    };

    index.selectCrewOnMap = function(crewId) {
        if (crewId != index.selectedCrewId) {
            index.selectedCrewId = crewId;
            index.crewMarkers[crewId].setIcon('rsc/images/transport_32_black.png');
            index.hoveredCrewIcon = index.crewMarkers[crewId].getIcon();

        } else {
            index.selectedCrewId = -1;
            index.hoveredCrewIcon = null;
            index.crewMarkers[crewId].setIcon('rsc/images/transport_24_red.png');
        }
    };

    index.onmouseleaveCallList = function(e) {
        console.log('onmouseleaveCallList');
        var callId = $(this).find('.id').text();
        if (callId != index.selectedCallId) {
            index.hoveredCallId = -1;
            index.callMarkers[callId].setIcon('rsc/images/people_24_red.png');
        }
    };

    index.onmouseenterCallList = function(e) {
        console.log('onmouseenterCallList');
        var callId = $(this).find('.id').text();
        if (callId != index.selectedCallId) {
            index.hoveredCallId = callId;
            index.hoveredCallIcon = index.callMarkers[callId].getIcon();
            index.callMarkers[callId].setIcon('rsc/images/people_24_black.png');
        }
    };

    index.onclickCallList = function(e) {
        console.log('onclickCallList');
        index.clearListSelection();
        index.clearMarkerSelection();
        var callListItem = $(this);
        var callId = callListItem.find('.id').text();
        if (callId != index.selectedCallId) {
            callListItem.addClass('selected');
        }
        index.selectCallOnMap(callId);
        if (index.calls[callId].crewId != null) {
            index.selectCrewOnMap(index.calls[callId].crewId);
        }
    };

    index.onmouseleaveCrewList = function(e) {
        console.log('onmouseleaveCrewList');
        var crewId = $(this).find('.id').text();
        if (crewId != index.selectedCrewId) {
            index.hoveredCrewId = -1;
            index.crewMarkers[crewId].setIcon('rsc/images/transport_24_red.png');
        }
    };

    index.onmouseenterCrewList = function(e) {
        console.log('onmouseenterCrewList');
        var crewId = $(this).find('.id').text();
        if (crewId != index.selectedCrewId) {
            index.hoveredCrewId = crewId;
            index.hoveredCrewIcon = index.crewMarkers[crewId].getIcon();
            index.crewMarkers[crewId].setIcon('rsc/images/transport_24_black.png');
        }
    };

    index.onclickCrewList = function(e) {
        index.clearListSelection();
        index.clearMarkerSelection();
        var crewListItem = $(this);
        var crewId = crewListItem.find('.id').text();
        console.log(index.crews[crewId]);
        if (crewId != index.selectedCrewId) {
            crewListItem.addClass('selected');
        }
        index.selectCrewOnMap(crewId);
        if (index.crews[crewId].callId != null) {
            index.selectCallOnMap(index.crews[crewId].callId);
        }
    };

})(index || (index = {}));
