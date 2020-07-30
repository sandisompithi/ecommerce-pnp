(function () {
    var head = document.getElementsByTagName('head')[0];
    var s = document.createElement('link');
    s.setAttribute('type', 'text/css');
    s.setAttribute('rel', 'stylesheet');
    s.setAttribute('href', './pnp/themes/profile/profile.css?' + dojoConfig.cacheBust);
    head.appendChild(s);

    // var adrum = document.createElement('script');
    // adrum.setAttribute('type', 'text/javascript');
    // adrum.setAttribute('src', './pnp/native/adrum.js');
    // head.appendChild(adrum);

    var ieEventBehaviour = document.attachEvent && typeof Windows === "undefined" && (typeof opera === "undefined" || opera.toString() != "[object Opera]");
    var onLoad = function (e) {
        e = e || window.event;
        var node = e.target || e.srcElement;
        if (e.type === "load" || /complete|loaded/.test(node.readyState)) {
            dojoLoaded();
        }
    };
    var dojoLoaded = function () {
        // Google Tag Magager
        require(['dojo/query', 'dojo/dom-construct', 'dojo/dom-attr'],
            function (query, domConstruct, domAttr) {
                // var gtmNoScript = domConstruct.create('noscript', null, dojo.query('body'), 'first');
                var body = dojo.query('body')[0];
                var gtmNoScript = domConstruct.create('noscript', null, body, 'first');
                var gtmIframe = domConstruct.create('iframe', {
                    'src': 'https://www.googletagmanager.com/ns.html?id=GTM-WDWF4P',
                    'height': '0',
                    'width': '0',
                    'style': 'display:none;visibility:hidden'
                }, gtmNoScript);
            });
        require(["pnp/profileclient/config"],
            function (config) {
                // if (config.getEnableAppDynamics()) {
                //     window['adrum-start-time'] = new Date().getTime();
                //     window['adrum-app-key'] = "'" + config.getAppDynamicsAppKey() + "'";
                // }
            });
        require(["pnp/profileclient/ui/MainView", "dojo/_base/window", "dojo/domReady!"],
            function (MainView, baseWindow) {
                var mainView = new MainView();
                mainView.placeAt(baseWindow.body());
                mainView.startup();
            });
    };
    var domOn = function (node, eventName, ieEventName, handler) {
        // Add an event listener to a DOM node using the API appropriate for the current browser;
        // return a function that will disconnect the listener.
        if (!ieEventBehaviour) {
            node.addEventListener(eventName, handler, false);
            return function () {
                node.removeEventListener(eventName, handler, false);
            };
        } else {
            node.attachEvent(ieEventName, handler);
            return function () {
                node.detachEvent(ieEventName, handler);
            };
        }
    };

    var d = document.createElement('script');
    d.setAttribute('type', 'text/javascript');
    d.setAttribute('src', './dojo/dojo.js?' + dojoConfig.cacheBust);
    domOn(d, "load", "onreadystatechange", onLoad);
    head.appendChild(d);
})();