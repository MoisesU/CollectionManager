/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

var chart = [["Error", 0]];

(function (){
    $.ajax({
            url: 'rservices/figs/statistics',
            method: 'GET',
            dataType:'json'
    })
    .done(function(json){
        /*Setting data obtained*/
        console.log(json);
        for(let q in json.recent){
            let thumbnail = "<div class='col-md-2 col-sm-4'>"
            +"<div class='thumbnail'>"
            +"<img class='img-rounded' alt='"+json.recent[q]["IMG_FIG"]+"' src='rec/"+json.recent[q]["IMG_FIG"]+"'>"
            +"<div class='caption'><p>"+json.recent[q]["SERIAL"]+"<br>"+json.recent[q]["NOM_FIG"]
            +"<br>"+json.recent[q]["FECHA_ADQ"]+"</p></div></div></div>";
            $("#recent").append(thumbnail);
        }
        chart = json["purchases"];
        $("#total").html("<h2>Valor total: "+new Intl.NumberFormat("es-MX", {style: "currency", currency: "MXN"}).format(json["total"][2])+"</h2>");
        $("#total").append("<h3>Total de figuras: "+parseInt(json["total"][1], 10)+"</h3>");
        $("#total").append("<h3>Total de figuras: "+parseInt(json["total"][0], 10)+"</h3>");
        
    }).fail(function(xhr, status, err) {
        console.log('Error al eviar: '+xhr.responseText+'\nError: '+status+"\nStatus: "+xhr.status);
    });
}());




