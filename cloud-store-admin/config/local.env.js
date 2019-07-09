
var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

'use strict'
debugger
module.exports =  merge(prodEnv,{
  NODE_ENV: '"local"',
  EVN_CONFIG:'"local"',
  API_ROOT:'"/api"'
})

