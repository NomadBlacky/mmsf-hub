if (process.env.NODE_ENV === "production") {
    const opt = require("./mmsf-hub-opt.js");
    opt.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./mmsf-hub-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./mmsf-hub-fastopt.js");
    fastOpt.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
