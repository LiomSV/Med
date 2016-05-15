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

    acall.crewId = 0;
    acall.callId = 0;

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
                        '<tr' + (i == 0 ? ' class="success"' : '') + '>' +
                            '<td>' + crew.id + '</td>' +
                            '<td>' + main.crewTypeString[crew.type] + '</td>' +
                            '<td>' + members + '</td>' +
                            '<td>' + (distance.meters / 1000 )+ ' км</td>' +
                            '<td></td>' +
                        '</tr>');

                    new google.maps.Marker({
                        draggable: true,
                        icon: i == 0 ? 'rsc/images/transport_32_red.png' : 'rsc/images/transport_24_red.png',
                        map: acall.map,
                        position: {
                            lat: crew.location.lat,
                            lng: crew.location.lng
                        }
                    });
                });
            }
        });
    };

    acall.onclickBtnAssign = function() {
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.call.assign,
            method: 'POST',
            data: {
                callId: acall.callId,
                crewId: acall.crewId
            },
            success: function (response) {
                if (response == 'ok') {
                    console.log(response);
                    $('#dc_link').get(0).click();
                }
            }
        })
    };

})(acall || (acall = {}));
