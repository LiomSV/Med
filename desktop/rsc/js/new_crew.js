$(document).ready(function() {

});

var ncrew = {};

(function (ncrew){

    ncrew.onclickBtnAdd = function() {
        var member = '';
        $('#members').find('.member').each(function(i, d) {
            console.log(d);
            if (member.length > 0) {
                member += ',';
            }
            member += d.value;
        });
        jQuery.ajax({
            url: conf.endpointPrefix + conf.endpoint.crew.add,
            method: 'POST',
            data: {
                type: $('#type').val(),
                driver: $('#driver').val(),
                carNumber: $('#carNumber').val(),
                member: member
            },
            success: function(response) {
                console.log(response);
                $('#registerNumber').text(response);
                $('#registerNumberPanel').removeClass('hidden');
            }
        });
    };

    ncrew.onSubmit = function() {

    }


})(ncrew || (ncrew = {}));