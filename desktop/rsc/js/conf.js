var conf = {};


(function(conf) {


    conf.endpointPrefix = "http://localhost:8080/rest";

    conf.endpoint = {
        call: {
            get:            "/call",
            add:            "/call/add",
            assign:         "/call/assign",
            list:           "/call/list",
            advice_crew:    "/call/advice_crew"
        },
        crew: {
            add:            "/crew/add",
            remove:         "/crew/remove",
            list:           "/crew/list",
            update_status:  "/crew/update_status"
        }
    };

    conf.callStatus = {
        AWAITING: 'AWAITING',
        MOVING_TO: 'MOVING_TO',
        TREATMENT: 'TREATMENT',
        TRANSPORTATION: 'TRANSPORTATION',
        FINISHED: 'FINISHED'
    };

    conf.callType = {
        URGENT: 'URGENT',
        IMMEDIATE: 'IMMEDIATE',
        EMERGENT: 'EMERGENT'
    };

    conf.crewStatus = {
        FREE: 'FREE',
        MOVING: 'MOVING',
        TREATMENT: 'TREATMENT',
        TRANSPORTING: 'TRANSPORTING',
        BREAK: 'BREAK'
    };

    conf.crewType = {
        PARAMEDIC: 'PARAMEDIC',
        DOCTOR: 'DOCTOR',
        SPECIALIZED: 'SPECIALIZED'
    };

    conf.callStatusOrd = {
        AWAITING: 0,
        MOVING_TO: 1,
        TREATMENT: 2,
        TRANSPORTATION: 3,
        FINISHED: 4
    };

    conf.callTypeOrd = {
        URGENT: 0,
        IMMEDIATE: 1,
        EMERGENT: 2
    };

    conf.crewStatusOrd = {
        FREE: 0,
        MOVING: 1,
        TREATMENT: 2,
        TRANSPORTING: 3,
        BREAK: 4
    };

    conf.crewTypeOrd = {
        PARAMEDIC: 0,
        DOCTOR: 1,
        SPECIALIZED: 2
    };

})(conf || (conf = {}));