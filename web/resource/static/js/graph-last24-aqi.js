var myChart = echarts.init(document.getElementById('pg_content_24h'), 'shine');

var option = {
    color: ['#79b05f'],
    tooltip : {trigger: 'axis'},
    legend: {data:['AQI']},
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ["23Day19H","23Day19H","23Day20H","23Day21H","23Day22H","23Day23H","24Day00H","24Day01H","24Day02H","24Day03H","24Day05H","24Day06H","24Day07H","24Day08H","24Day09H","24Day10H","24Day11H","24Day12H","24Day13H"],
            axisLine: {lineStyle : {color: '#d4d4d4'}}
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLine: {lineStyle : {color: '#d4d4d4'}}
        }
    ],
    series : [
        {
            name:'AQI',
            type:'line',
            data:[36.0,36.0,31.0,31.0,35.0,24.0,23.0,23.0,23.0,22.0,22.0,22.0,22.0,23.0,23.0,22.0,26.0,24.0,36.0]
        }
    ]
};
myChart.setOption(option);