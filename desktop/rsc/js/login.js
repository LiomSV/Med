var login = {};

(function(login) {

    login.enter = function() {

        if ($('#fldLogin').val() != 'vstasenia') {
            $('.error').removeClass('hidden');
        } else {
            $('#enter_link').get(0).click();
        }

    }

})(login || (login = {}));