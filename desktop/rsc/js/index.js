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

    index.updateCalls();
    var updateCallsTimer = setInterval(index.updateCalls, 4000);

});

var index = {};

(function (index){

    index.markerCalls = {};

    index.makeAutoHight = function() {
        $('.auto-height').css('height', $(window).height() - 133);
    };

    index.updateCalls = function() {
        jQuery.ajax({
            url: 'http://localhost:8080/rest/call/getall',
            method: 'GET',
            success: function(response) {
                console.log(response);
                console.log(index.markerCalls);
                $('#calls_count').text(response.length);
                $('#calls_table').empty();
                response.forEach(function(call) {
                    $('#calls_table').append(
                        '<div class="col-xs-1">' +
                            '<b>' + call.id + '</b>' +
                        '</div>' +
                        '<div class="col-xs-3">' +
                            '<b>' + call.lastname + '</b>' +
                        '</div>' +
                        '<div class="col-xs-4">' +
                            '<b>' + call.address + '</b>' +
                        '</div>' +
                        '<div class="col-xs-2">' +
                            '<b>' + call.reason + '</b>' +
                        '</div>' +
                        '<div class="col-xs-2">' +
                        '</div>');
                    if (index.markerCalls[call.location.lat + ':' + call.location.lng] == null) {
                        index.markerCalls[call.location.lat + ':' + call.location.lng] = new google.maps.Marker({
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
            }
        })
    };

})(index || (index = {}));
