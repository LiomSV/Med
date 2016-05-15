$(document).ready(function() {

});

var main = {};

(function (main){

    main.getUrlParameter = function (sParam) {
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++)
        {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] == sParam)
            {
                return sParameterName[1];
            }
        }
    };

    main.localDateToString = function(localDate) {
        if (localDate != null) {
            return localDate.dayOfMonth + '.' + localDate.monthValue + '.' + localDate.year;
        } else {
            return '';
        }
    };

    main.sexToString = {
        MALE: 'Муж.', 
        FEMALE: 'Жен.', 
        UNKNOWN: '-' 
    };

    main.callStatusString = {
        AWAITING: 'Ожидание', 
        MOVING_TO: 'Выехали', 
        TREATMENT: 'На месте', 
        TRANSPORTATION: 'Траспортировка', 
        FINISHED: 'Завершен' 
    };     

    main.callTypeString = {
        URGENT: 'Неотложный',  
        IMMEDIATE: 'Срочный',  
        EMERGENT: 'Экстренный'  
    };

    main.crewStatusString = {
        FREE: 'Свободна',
        MOVING: 'В пути',
        TREATMENT: 'На месте',
        TRANSPORTING: 'Траспортировка',
        BREAK: 'Перерыв'
    };
    
    main.crewTypeString = {
        PARAMEDIC: 'фельдшер.',
        DOCTOR: 'врач.',
        SPECIALIZED: 'спец.'
    }

})(main || (main = {}));
