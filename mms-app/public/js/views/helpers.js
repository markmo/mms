define(['handlebars', 'underscore'], function (Handlebars, _) {

    Handlebars.registerHelper('humanize', function (value) {
        return new Handlebars.SafeString(
            moment(value).fromNow()
        );
    });

    Handlebars.registerHelper('loop', function (context, options) {
        var fn = options.fn,
            inverse = options.inverse,
            ret = '';
        if (context && context.length > 0) {
            for (var i = 0, j = context.length; i < j; i += 1) {
                ret = ret + fn(_.extend({}, context[i], {i: i}));
            }
        } else {
            ret = inverse(this);
        }
        return ret;
    });

    Handlebars.registerHelper('foreach', function (arr, options) {
        if (options.inverse && !arr.length)
            return options.inverse(this);

        return arr.map(function (item, index) {
            item.$index = index;
            item.$first = index === 0;
            item.$last  = index === arr.length - 1;
            item.$more  = index < arr.length - 1;
            return options.fn(item);
        }).join('');
    });

    Handlebars.registerHelper('isRoleSelected', function (context, options) {
        var hash = options.hash;
        var roleId = context.id;
        var term = hash.term;
        var personId = hash.person.id;
        var isSelected = false;
        if (term.people) {
            for (var i = 0, n = term.people.length; i < n; i += 1) {
                var r = term.people[i];
                if (r.person.id === personId &&
                    r.stakeholderRole.id === roleId)
                {
                    isSelected = true;
                    break;
                }
            }
        }
        return isSelected ? "selected" : "";
    });

    Handlebars.registerHelper('hasPrevious', function (pageableCollection, block) {
        if (pageableCollection.hasPrevious()) {
            return block.fn(this);
        } else {
            return block.inverse(this);
        }
    });

    Handlebars.registerHelper('hasNext', function (pageableCollection, block) {
        if (pageableCollection.hasNext()) {
            return block.fn(this);
        } else {
            return block.inverse(this);
        }
    });

    Handlebars.registerHelper('order', function (pageableCollection) {
        return pageableCollection.state.order === -1 ? "headerSortUp" : "headerSortDown";
    });

    Handlebars.registerHelper('eq', function (left, right, block) {
        if (left === right) {
            return block.fn(this);
        } else {
            return block.inverse(this);
        }
    });

    Handlebars.registerHelper('notEq', function (left, right, block) {
        if (left === right) {
            return block.inverse(this);
        } else {
            return block.fn(this);
        }
    });

    Handlebars.registerHelper('sortableHeading', function (page, field, label) {
        var sortKey, sortOrder;
        if (page.state) {
            sortKey = page.state.sortKey;
            sortOrder = page.state.order === -1 ? 'headerSortUp' : 'headerSortDown';
        } else {
            sortKey = page.sortKey;
            sortOrder = page.sortOrder;
        }
        label = Handlebars.Utils.escapeExpression(label);
        var result;
        if (sortKey === field) {
            result = '<th class="' + sortOrder + '">';
        } else {
            result = '<th>';
        }
        result += '<a href="#" data-sort="' + field + '">' + label + '</a></th>';
        return new Handlebars.SafeString(result);
    });

    Handlebars.registerHelper('page', function (pageableCollection, block) {
        if (pageableCollection.length) {
            return block.fn(_.extend(this, {list: pageableCollection.toJSON()}));
        } else {
            return block.inverse(this);
        }
    });

    Handlebars.registerHelper('crudClass', function (a) {
        switch('' + a) {
            case 'C+':
                return 'create active';
            case 'C-':
                return 'create';
            case 'R+':
                return 'read active';
            case 'R-':
                return 'read';
            case 'U+':
                return 'update active';
            case 'U-':
                return 'update';
            case 'D+':
                return 'delete active';
            case 'D-':
                return 'delete';
            default:
                return '';
        }
    });

});
