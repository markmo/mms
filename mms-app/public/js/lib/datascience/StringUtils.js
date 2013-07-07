define(function () {

    function camelCase(str) {
        var ch, retval = [];
        if (typeof str === 'string') {
            for (var i = 0, n = str.length; i < n; i += 1) {
                ch = str.charAt(i);
                if (i > 0 && ch === '_') {
                    retval.push(str.charAt(i += 1).toUpperCase());
                } else {
                    retval += ch;
                }
            }
        }
        return retval.join('');
    }

    function convertCamelCaseToSpaced(str) {
        var ch, retval = [];
        if (typeof str === 'string') {
            for (var i = 0, n = str.length; i < n; i += 1) {
                ch = str.charAt(i);
                if (i > 0 && isUpperCase(str, i) && ! (isUpperCase(str, i - 1) || ch === ' ')) {
                    retval.push(' ', ch);
                } else if (i > 0 && isNumber(str, i) && ! (isNumber(str, i - 1) || ch === ' ')) {
                    retval.push(' ', ch);
                } else if (ch === '_' || ch === '-') {
                    retval.push(' ');
                } else {
                    retval.push(ch);
                }
            }
        }
        return retval.join('');
    }

    function endsWith(str, match) {
        if (typeof str === 'string' && typeof match === 'string') {
            return str.lastIndexOf(match) === (str.length - match.length);
        }
        return false;
    }

    function isLowerCase(str, i) {
        if (typeof str === 'string') {
            if (i > -1 && i < str.length) {
                var code = str.charCodeAt(i);
                return (code > 96 && code < 123);
            }
        }
        return false;
    }

    function isNumber(str, i) {
        if (typeof str === 'string') {
            if (i > -1 && i < str.length) {
                var code = str.charCodeAt(i);
                return (code > 47 && code < 58);
            }
        }
        return false;
    }

    function isUpperCase(str, i) {
        if (typeof str === 'string') {
            if (i > -1 && i < str.length) {
                var code = str.charCodeAt(i);
                return (code > 64 && code < 91);
            }
        }
        return false;
    }

    function parseBoolean(value, defaultValue) {
        var val;
        if (typeof value === 'boolean') {
            return value;
        } else if (typeof str === 'string') {
            val = trim(str).toLowerCase();
            if (val === 'true') {
                return true;
            } else if (val === 'false') {
                return false;
            } else {
                return parseBoolean(defaultValue);
            }
        } else {
            throw new Error('Cannot determine boolean from value: ' + value);
        }
    }

    function startLowerCase(str) {
        return str.substr(0, 1).toLowerCase() + str.substr(1);
    }

    function underscore(str) {
        var ch, retval = [];
        if (typeof str === 'string') {
            for (var i = 0, n = str.length; i < n; i += 1) {
                ch = str.charAt(i);
                if (i > 0 && isUpperCase(str, i)) {
                    retval.push('_', ch);
                } else if (i > 0 && isNumber(str, i)) {
                    retval.push('_', ch);
                } else {
                    retval.push(ch);
                }
            }
        }
        return retval.join('').toLowerCase();
    }

    return {
        camelCase: camelCase,
        convertCamelCaseToSpaced: convertCamelCaseToSpaced,
        endsWith: endsWith,
        isLowerCase: isLowerCase,
        isNumber: isNumber,
        isUpperCase: isUpperCase,
        parseBoolean: parseBoolean,
        startLowerCase: startLowerCase,
        underscore: underscore
    };
});
