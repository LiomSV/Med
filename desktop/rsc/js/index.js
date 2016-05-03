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

    index.makeAutoHight = function() {
        $('.auto-height').css('height', $(window).height() - 133);
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
                            '<td></td>' +
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
                        '<th>Гос номер</th>' +
                        '<th></th>' +
                    '</tr>');
                response.forEach(function(crew) {
                    $('#crews_table').append(
                        '<tr>' +
                            '<td>' + crew.id + '</td>' +
                            '<td>' + crew.status + '</td>' +
                            '<td>' + crew.carNumber + '</td>' +
                            '<td></td>' +
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
            }
        })
    }

})(index || (index = {}));
