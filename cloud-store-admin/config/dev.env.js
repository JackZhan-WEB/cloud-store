var merge = require('webpack-merge')
var prodEnv = require('./local.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"dev"'
});
