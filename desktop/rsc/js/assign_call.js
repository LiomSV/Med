$(document).ready(function() {

    acall.callId = main.getUrlParameter('id');
    if (acall.callId == null) {
        acall.callId = 0;
    }

    acall.makeAutoHight();
    $(window).resize(acall.makeAutoHight);

    var mapCanvas = document.getElementById('map');
    var mapOptions = {
        center: new google.maps.LatLng(53.9, 27.5667),
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    acall.map = new google.maps.Map(mapCanvas, mapOptions);

    // index.update();
    // var updateCallsTimer = setInterval(index.update, 4000);

    acall.getData();

});

var acall = {};

(function (acall){

    acall.callId = 0;
    acall.selectedCrewId = 0;
    acall.crewMarkers = {};
    acall.hoveredCrewId = -1;
    acall.hoveredCrewIcon = null;

    acall.makeAutoHight = function() {
        $('.auto-height').css('height', $(window).height() - 183);
    };

    acall.getData = function() {
        acall.getCall();
        acall.getAdvisedCrews();
    };

    acall.getCall = function() {
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.call.get,
            method: 'GET',
            data: {
                id: acall.callId
            },
            success: function (call) {
                console.log(call);
                $('#id').text(call.id);
                $('#status').text(main.callStatusString[call.status]);
                $('#type').text(main.callTypeString[call.type]);
                $('#address').text(call.address);
                $('#phoneNumber').text(call.phoneNumber);
                $('#firstname').text(call.firstname);
                $('#fathername').text(call.fathername);
                $('#lastname').text(call.lastname);
                $('#sex').text(main.sexToString[call.sex]);
                $('#birthDate').text(main.localDateToString(call.birthDate));
                $('#reason').text(call.reason);
                $('#comment').text(call.comment);

                new google.maps.Marker({
                    icon: 'rsc/images/people_24_red.png',
                    map: acall.map,
                    position: {
                        lat: call.location.lat,
                        lng: call.location.lng
                    }
                });
            }
        });
    };

    acall.getAdvisedCrews = function() {
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.call.advice_crew,
            method: 'POST',
            data: {
                callId: acall.callId
            },
            success: function (distances) {
                console.log(distances);
                $('#crews_table').html(
                    '<tr>' +
                        '<th>№</th>' +
                        '<th>Тип</th>' +
                        '<th>Персонал</th>' +
                        '<th>Расстояние</th>' +
                        '<th></th>' +
                    '</tr>');
                distances.forEach(function(distance, i) {
                    var crew = distance.object;
                    if (i == 0) {
                        acall.crewId = crew.id;
                    }
                    var members = '';
                    crew.members.forEach(function(d) {
                        if (members.length > 0)
                            members += ', ';
                        members += d;
                    });
                    $('#crews_table').append(
                        '<tr class="crew-list-item' + (i == acall.selectedCrewId ? ' selected' : '') + '">' +
                            '<td class="id">' + crew.id + '</td>' +
                            '<td>' + main.crewTypeString[crew.type] + '</td>' +
                            '<td>' + members + '</td>' +
                            '<td>' + (distance.meters / 1000 )+ ' км</td>' +
                            '<td></td>' +
                        '</tr>');

                    acall.crewMarkers[crew.id] = new google.maps.Marker({
                        draggable: true,
                        icon: i == acall.selectedCrewId ? 'rsc/images/transport_32_black.png' : 'rsc/images/transport_24_red.png',
                        map: acall.map,
                        position: {
                            lat: crew.location.lat,
                            lng: crew.location.lng
                        }
                    });
                });

                var crewListItem = $('.crew-list-item');
                crewListItem.off()
                    .on('click', acall.onclickCrewList)
                    .on('mouseenter', acall.onmouseenterCrewList)
                    .on('mouseleave', acall.onmouseleaveCrewList);
            }
        });
    };

    acall.onclickBtnAssign = function() {
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.call.assign,
            method: 'POST',
            data: {
                callId: acall.callId,
                crewId: acall.selectedCrewId
            },
            success: function (response) {
                if (response == 'ok') {
                    console.log(response);
                    $('#dc_link').get(0).click();
                }
            }
        })
    };

    acall.clearMarkerSelection = function() {
        var selectedCrewMarker = acall.crewMarkers[acall.selectedCrewId];
        if (selectedCrewMarker) {
            selectedCrewMarker.setIcon('rsc/images/transport_24_red.png');
        }
    };

    acall.clearListSelection = function() {
        $('#crews_table').find('.selected').removeClass('selected');
    };
    acall.selectCrewOnMap = function(crewId) {
        acall.selectedCrewId = crewId;
        acall.crewMarkers[crewId].setIcon('rsc/images/transport_32_black.png');
        acall.hoveredCrewIcon = acall.crewMarkers[crewId].getIcon();
    };

    acall.onmouseleaveCrewList = function(e) {
        console.log('onmouseleaveCrewList');
        var crewId = $(this).find('.id').text();
        if (crewId != acall.selectedCrewId) {
            acall.hoveredCrewId = -1;
            acall.crewMarkers[crewId].setIcon('rsc/images/transport_24_red.png');
        }
    };

    acall.onmouseenterCrewList = function(e) {
        console.log('onmouseenterCrewList');
        var crewId = $(this).find('.id').text();
        if (crewId != acall.selectedCrewId) {
            acall.hoveredCrewId = crewId;
            acall.hoveredCrewIcon = acall.crewMarkers[crewId].getIcon();
            acall.crewMarkers[crewId].setIcon('rsc/images/transport_24_black.png');
        }
    };

    acall.onclickCrewList = function(e) {
        acall.clearListSelection();
        acall.clearMarkerSelection();
        var crewListItem = $(this);
        var crewId = crewListItem.find('.id').text();
        crewListItem.addClass('selected');
        acall.selectCrewOnMap(crewId);
    };

})(acall || (acall = {}));
