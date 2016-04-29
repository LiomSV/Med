$(document).ready(function() {

});

var nc = {};

(function (nc){

    nc.onclickBtnRegister = function() {
        jQuery.ajax({
            url: 'http://localhost:8080/rest/call/add',
            method: 'POST',
            data: {
                address: $('#address').val()
            },
            success: function(response) {
                if (response == 'ok') {
                    console.log(response);
                    $('#dc_link').get(0).click();
                }
            }
        });
    }

})(nc || (nc = {}));
