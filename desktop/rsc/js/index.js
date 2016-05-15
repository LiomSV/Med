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
                console.log(response);
                console.log(index.callMarkers);
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
                    $('#calls_table').append(
                        '<tr>' +
                            '<td>' + call.id + '</td>' +
                            '<td>' + call.lastname + '</td>' +
                            '<td>' + call.address + '</td>' +
                            '<td>' + call.reason + '</td>' +
                            '<td><span class="glyphicon glyphicon-list-alt"></span></td>' +
                        '</tr>');
                    counts[call.type] += 1;
                    if (index.callMarkers[call.id] == null) {
                        index.callMarkers[call.id] = new google.maps.Marker({
                            draggable: true,
                            icon: 'rsc/images/people_24_red.png',
                            map: index.map,
                            position: {
                                lat: call.location.lat,
                                lng: call.location.lng
                            }
                        });
                    }
                });
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
                console.log(response);
                console.log(index.callMarkers);
                $('#crews_table').html(
                    '<tr>' +
                        '<th>№</th>' +
                        '<th>Статус</th>' +
                        '<th>Водитель</th>' +
                        '<th>Персонал</th>' +
                        '<th></th>' +
                    '</tr>');
                response.forEach(function(crew) {
                    var members = '';
                    crew.members.forEach(function(d) {
                        if (members.length > 0)
                            members += ', ';
                        members += d;
                    });
                    $('#crews_table').append(
                        '<tr class="crew-list-item">' +
                            '<td class="id">' + crew.id + '</td>' +
                            '<td>' + main.crewStatusString[crew.status] + '</td>' +
                            '<td>' + crew.driver + '</td>' +
                            '<td>' + members + '</td>' +
                            '<td><span class="glyphicon glyphicon-list-alt"></span></td>' +
                        '</tr>');
                    if (crew.location && index.crewMarkers[crew.id] == null) {
                        index.crewMarkers[crew.id] = new google.maps.Marker({
                            draggable: true,
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
                crewListItem.click(index.onclickCrewList);
                crewListItem.mouseenter(index.onmouseenterCrewList);
                crewListItem.mouseleave(index.onmouseleaveCrewList);
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

    index.clearMarkerHover = function() {
        var hoveredCallMarker = index.callMarkers[index.hoveredCallId];
        if (hoveredCallMarker) {
            hoveredCallMarker.setIcon(index.hoveredCallIcon || 'rsc/images/people_24_red.png');
        }
        var hoveredCrewMarker = index.crewMarkers[index.hoveredCrewId];
        if (hoveredCrewMarker) {
            hoveredCrewMarker.setIcon(index.hoveredCrewIcon || 'rsc/images/transport_24_red.png');
        }
    };

    index.onmouseleaveCrewList = function(e) {
        console.log('onmouseleaveCrewList');
        var tr = $(e.currentTarget);
        tr.removeClass('success');
        var crewId = tr.find('.id').text();
        if (crewId != index.selectedCrewId) {
            index.hoveredCrewId = -1;
            index.crewMarkers[crewId].setIcon('rsc/images/transport_24_red.png');
        }
    };

    index.onmouseenterCrewList = function(e) {
        console.log('onmouseenterCrewList');
        var tr = $(e.currentTarget);
        tr.addClass('success');
        var crewId = tr.find('.id').text();
        if (crewId != index.selectedCrewId) {
            index.hoveredCrewId = crewId;
            index.hoveredCrewIcon = index.crewMarkers[crewId].getIcon();
            index.crewMarkers[crewId].setIcon('rsc/images/transport_24_blue.png');
        }
    };

    index.onclickCrewList = function(e) {
        console.log('onclickCrewList');
        index.clearMarkerSelection();
        var crewId = $(e.currentTarget).find('.id').text();
        if (crewId != index.selectedCrewId) {
            index.selectedCrewId = crewId;
            index.crewMarkers[crewId].setIcon('rsc/images/transport_32_blue.png');
            index.hoveredCrewIcon = index.crewMarkers[crewId].getIcon();
        } else {
            index.selectedCrewId = -1;
            index.hoveredCrewIcon = null;
            index.crewMarkers[crewId].setIcon('rsc/images/transport_24_blue.png');
        }
    }

})(index || (index = {}));
