define(function () {

    var plurals = [
        [/(quiz)$/i,                     "$1zes"],
        [/^(ox)$/i,                      "$1en"],
        [/([m|l])ouse$/i,                "$1ice"],
        [/(matr|vert|ind)ix|ex$/i,       "$1ices"],
        [/(x|ch|ss|sh)$/i,               "$1es"],
        [/([^aeiouy]|qu)y$/i,            "$1ies"],
        [/(hive)$/i,                     "$1s"],
        [/(?:([^f])fe|([lr])f)$/i,       "$1$2ves"],
        [/(shea|lea|loa|thie)f$/i,       "$1ves"],
        [/sis$/i,                        "ses"],
        [/([ti])um$/i,                   "$1a"],
        [/(tomat|potat|ech|her|vet)o$/i, "$1oes"],
        [/(bu)s$/i,                      "$1ses"],
        [/(alias|status)$/i,             "$1es"],
        [/(octop)us$/i,                  "$1i"],
        [/(ax|test)is$/i,                "$1es"],
        [/(us)$/i,                       "$1es"],
        [/s$/i,                          "s"],
        [/$/i,                           "s"]
    ];

    var singulars = [
        [/(quiz)zes$/i,             	 "$1"],
        [/(matr)ices$/i,            	 "$1ix"],
        [/(vert|ind)ices$/i,        	 "$1ex"],
        [/^(ox)en$/i,               	 "$1"],
        [/(alias|status)es$/i,      	 "$1"],
        [/(octop|vir)i$/i,          	 "$1us"],
        [/(cris|ax|test)es$/i,      	 "$1is"],
        [/(shoe)s$/i,               	 "$1"],
        [/(o)es$/i,                 	 "$1"],
        [/(bus)es$/i,               	 "$1"],
        [/([m|l])ice$/i,            	 "$1ouse"],
        [/(x|ch|ss|sh)es$/i,        	 "$1"],
        [/(m)ovies$/i,              	 "$1ovie"],
        [/(s)eries$/i,              	 "$1eries"],
        [/([^aeiouy]|qu)ies$/i,     	 "$1y"],
        [/([lr])ves$/i,             	 "$1f"],
        [/(tive)s$/i,               	 "$1"],
        [/(hive)s$/i,               	 "$1"],
        [/(li|wi|kni)ves$/i,        	 "$1fe"],
        [/(shea|loa|lea|thie)ves$/i,	 "$1f"],
        [/(^analy)ses$/i,           	 "$1sis"],
        [/((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$/i, "$1$2sis"],
        [/([ti])a$/i,               	 "$1um"],
        [/(n)ews$/i,                	 "$1ews"],
        [/(h|bl)ouses$/i,           	 "$1ouse"],
        [/(corpse)s$/i,             	 "$1"],
        [/(us)es$/i,                	 "$1"],
        [/s$/i,                     	 ""]
    ];

    var irregulars = [
        ["move",   "moves"],
        ["foot",   "feet"],
        ["goose",  "geese"],
        ["sex",    "sexes"],
        ["child",  "children"],
        ["man",    "men"],
        ["tooth",  "teeth"],
        ["person", "people"]
    ];

    var uncountables = [
        "sheep",
        "fish",
        "deer",
        "series",
        "species",
        "money",
        "rice",
        "information",
        "equipment"
    ];

    function enumerate(count, str) {
        if (count === 1) {
            return '1 ' + str;
        } else {
            return count.toString() + ' ' + pluralize(str);
        }
    }

    function pluralize(str) {
        var item,
            i, n,
            pattern,
            result;
        if (uncountables.indexOf(str.toLowerCase()) !== -1) {
            return str;
        }
        for (i = 0, n < irregulars.length; i < n; i += 1) {
            item = irregulars[i];
            pattern = new RegExp(item[0] + '$', 'i');
            result = item[1];
            if (pattern.test(str)) {
                return str.replace(pattern, result);
            }
        }
        for (i = 0, n = plurals.length; i < n; i += 1) {
            item = plurals[i];
            pattern = item[0];
            result = item[1];
            if (pattern.test(str)) {
                return str.replace(pattern, result);
            }
        }
        return str;
    }

    function singularize(str) {
        var item,
            i, n,
            pattern,
            result;
        if (uncountables.indexOf(str.toLowerCase()) !== -1) {
            return str;
        }
        for (i = 0, n < irregulars.length; i < n; i += 1) {
            item = irregulars[i];
            pattern = new RegExp(item[1] + '$', 'i');
            result = item[0];
            if (pattern.test(str)) {
                return str.replace(pattern, result);
            }
        }
        for (i = 0, n = singulars.length; i < n; i += 1) {
            item = singulars[i];
            pattern = item[0];
            result = item[1];
            if (pattern.test(str)) {
                return str.replace(pattern, result);
            }
        }
        return str;
    }

    return {
        enumerate: enumerate,
        pluralize: pluralize,
        singularize: singularize
    }
});
