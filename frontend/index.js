window.onload = function () {

    let dataSets = []



    // getPrices('highestPrices');

    // getPrices('lowestPrices');

    // getPrices('leastSquareHigh');

    // getPrices('leastSquareLow');

    // getPrices('SimpleMovingAverage/5');

    // getPrices('WeightedMovingAverage/5');

    // getPrices('ExponentialMovingAverage/5');

    getPrices('HullMovingAverage/9');

    // getSupportResistanceLevels('resistance');

    // getSupportResistanceLevels('support');




    function getSupportResistanceLevels(endpoint) {
        fetch(`http://localhost:8080/${endpoint}`).then(resp => resp.json()).then(resp => {
            // var dataPoints = [];

            for (var i = 0; i < resp.length; i += 1) {
                dataPoints = [];
                for (let j = 0; j < 1000; j += 1) {
                    dataPoints.push({
                        x: j,
                        y: resp[i]
                    });
                }
                console.log(dataPoints);
                dataSets.push({ type: "line", dataPoints: dataPoints });
                // drawChart(dataSets);

            }
            drawChart(dataSets)


        })
    }

    function getPrices(endpoint) {
        fetch(`http://localhost:8080/${endpoint}`).then(resp => resp.json()).then(resp => {


            var dataPoints = [];

            for (var i = 0; i < resp.length; i += 1) {
                dataPoints.push({
                    x: i,
                    y: resp[i].y
                });
            }

            dataSets.push({ type: "line", dataPoints: dataPoints });
            // console.log(dataSets)
            drawChart(dataSets);

        })
    }

    function drawChart() {
        var options = {
            zoomEnabled: true,
            animationEnabled: true,
            title: {
                text: "Try Zooming - Panning"
            },
            axisY: {
                includeZero: false,
                lineThickness: 1
            },
            data: dataSets
        };
        var chart = new CanvasJS.Chart("chartContainer", options);
        chart.render();
    }

    // fetch("http://localhost:8080/highestPrices").then(resp => resp.json()).then(resp => {
    //     this.console.log(resp);
    //     list = [];
    //     for (i = 0; i < 200; i++) {
    //         list.push((Math.random() * 10) + 1);
    //     }


    //     var limit = 2000;
    //     var y = 100;

    //     var data = [];
    //     let data2 = [];

    //     let dataSeries = {};
    //     let dataSeries2 = {};

    //     var dataPoints = [];
    //     let dataPoints2 = [];

    //     for (var i = 0; i < resp.length; i += 1) {
    //         y += Math.round(Math.random() * 10 - 5);
    //         dataPoints.push({
    //             x: i,
    //             y: resp[i].y
    //         });
    //         dataPoints2.push({
    //             x: i,
    //             y: y
    //         })
    //     }

    //     dataSeries.type = 'line';
    //     dataSeries2.type = 'line';

    //     dataSeries.dataPoints = dataPoints;
    //     dataSeries.dataPoints2 = dataPoints2;

    //     data.push(dataSeries);
    //     data2.push(dataSeries2);

    //     //Better to construct options first and then pass it as a parameter
    //     var options = {
    //         zoomEnabled: true,
    //         animationEnabled: true,
    //         title: {
    //             text: "Try Zooming - Panning"
    //         },
    //         axisY: {
    //             includeZero: false,
    //             lineThickness: 1
    //         },
    //         data: [
    //             {
    //                 type: "line",
    //                 dataPoints: dataPoints
    //             },
    //             {
    //                 type: "line",
    //                 dataPoints: dataPoints2
    //             },
    //         ]
    //     };






    //     var chart = new CanvasJS.Chart("chartContainer", options);
    //     var startTime = new Date();
    //     chart.render();
    //     var endTime = new Date();
    //     // document.getElementById("timeToRender").innerHTML = "Time to Render: " + (endTime - startTime) + "ms";
    // })



}
