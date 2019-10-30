// http://localhost:8080/chartdata
window.onload = function() {
    
    // fetch("http://localhost:8080/chartdata")
    // .then(resp => resp.json())
    // .then(resp => {
    //     this.console.log(resp);
    //     list = [];
    //     for(i=0; i<200; i++){
    //         list.push((Math.random() * 10) + 1);
    //     }
    //     const myChart = new Chart(document.getElementById("myChart"), {
    //         type: 'line',
    //         data: {
    //           labels: [1500,1600,1700,1750,1800,1850,1900,1950,1999,2050],
    //           datasets: [{ 
    //               data: list,
    //               label: "Africa",
    //               borderColor: "#3e95cd",
    //               fill: false
    //             }
    //           ]
    //         },
    //         options: {
    //           title: {
    //             display: true,
    //             text: 'World population per region (in millions)'
    //           }
    //         }
    //       });
    // })
    list = [];
    list2 = [];
        for(i=0; i<2000; i++){
            list2.push(i)
            list.push((Math.random() * 10) + 1);
        }
        const myChart = new Chart(document.getElementById("myChart"), {
            type: 'line',
            data: {
                labels: list2,
              datasets: [{ 
                  data: list,
                  label: "Africa",
                  borderColor: "#3e95cd",
                  fill: false
                }
              ]
            },
            options: {
              title: {
                display: true,
                text: 'World population per region (in millions)'
              }
            }
          });
        
    

      
};