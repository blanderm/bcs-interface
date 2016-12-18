angular.module('hopologybrewing-bcs', [])
    .controller('outputController', function ($scope, $http) {
        console.log('outputController');
        $http.get('/output').
            then(function (response) {
                $scope.outputs = response.data;
            });

    })

    .controller('chartController', function ($scope, $http) {
        console.log('chartController');
        $http.get('/temp/0').
            then(function (response) {
                Highcharts.chart('temp-gauge0', {

                    chart: {
                        type: 'gauge',
                        plotBackgroundColor: null,
                        plotBackgroundImage: null,
                        plotBorderWidth: 0,
                        plotShadow: false
                    },

                    title: {
                        text: response.data[0].name + "<br>(" + response.data[0].setpoint + " SP)"
                    },

                    pane: {
                        startAngle: -150,
                        endAngle: 150,
                        background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                    },

                    // the value axis
                    yAxis: {
                        min: 35,
                        max: 80,

                        minorTickInterval: 'auto',
                        minorTickWidth: 1,
                        minorTickLength: 10,
                        minorTickPosition: 'inside',
                        minorTickColor: '#666',

                        tickPixelInterval: 30,
                        tickWidth: 2,
                        tickPosition: 'inside',
                        tickLength: 10,
                        tickColor: '#666',
                        labels: {
                            step: 2,
                            rotation: 'auto'
                        },
                        plotBands: [{
                            from: 65,
                            to: 70,
                            color: '#606060'
                        },
                            {
                                from: ((response.data[0].setpoint > 0) ? (response.data[0].setpoint - 0.5) : 68),
                                to: ((response.data[0].setpoint > 0) ? (response.data[0].setpoint + 0.5) : 68),
                                color: ((response.data[0].setpoint > 0) ? '#55BF3B' : '#606060')
                            }]
                    },

                    series: response.data
                })
            });

        $http.get('/temp/1').
            then(function (response) {
                Highcharts.chart('temp-gauge1', {

                    chart: {
                        type: 'gauge',
                        plotBackgroundColor: null,
                        plotBackgroundImage: null,
                        plotBorderWidth: 0,
                        plotShadow: false
                    },

                    title: {
                        text: response.data[0].name + "<br>(" + response.data[0].setpoint + " SP)"
                    },

                    pane: {
                        startAngle: -150,
                        endAngle: 150,
                        background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                    },

                    // the value axis
                    yAxis: {
                        min: 35,
                        max: 80,

                        minorTickInterval: 'auto',
                        minorTickWidth: 1,
                        minorTickLength: 10,
                        minorTickPosition: 'inside',
                        minorTickColor: '#666',

                        tickPixelInterval: 30,
                        tickWidth: 2,
                        tickPosition: 'inside',
                        tickLength: 10,
                        tickColor: '#666',
                        labels: {
                            step: 2,
                            rotation: 'auto'
                        },
                        plotBands: [{
                            from: 65,
                            to: 70,
                            color: '#606060'
                        },
                            {
                                from: ((response.data[0].setpoint > 0) ? (response.data[0].setpoint - 0.5) : 68),
                                to: ((response.data[0].setpoint > 0) ? (response.data[0].setpoint + 0.5) : 68),
                                color: ((response.data[0].setpoint > 0) ? '#55BF3B' : '#606060')
                            }]
                    },

                    series: response.data
                })
            })
    });