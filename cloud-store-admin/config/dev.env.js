var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  EVN_CONFIG:'"local"',
  API_ROOT:'"http://development:9992"'
})
