$(document).ready(function() {

});

var nc = {};

(function (nc){

    nc.onclickBtnRegister = function() {
        jQuery.ajax({
            url: 'http://localhost:8080/rest/call/add',
            method: 'POST',
            data: {
                firstname: $('#firstname').val(),
                fathername: $('#fathername').val(),
                lastname: $('#lastname').val(),
                phoneNumber: $('#phoneNumber').val(),
                address: $('#address').val(),
                reason: $('#reason').val(),
                comment: $('#comment').val()
                // TODO add type
                // TODO add sex
                // TODO add birthdate
            },
            success: function(response) {
                if (response != 'error') {
                    console.log(response);
                    var assignLink = $('#assign_link');
                    assignLink.attr('href', assignLink.attr('href') + '?id=' + response);
                    assignLink.get(0).click();
                }
            }
        });
    }

})(nc || (nc = {}));
