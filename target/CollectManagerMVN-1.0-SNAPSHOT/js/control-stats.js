/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

var month = ["Meses", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

function capEachWord(s){
    let arr = s.trim().toLowerCase().split(' ');
    for (var i = 0; i < arr.length; i++){
        arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].substring(1);
    }
    return arr.join(' ');
}

function formMoney(num){
    return new Intl.NumberFormat("es-MX", {style: "currency", currency: "MXN"}).format(num);
}

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
        $("#total").html("<h1>Valor total: "+formMoney(json["total"][2])+"</h1>");
        $("#total").append("<h2>Total de figuras: "+parseInt(json["total"][1], 10)+"</h2>");
        $("#total").append("<h2>Total de cajas: "+parseInt(json["total"][0], 10)+"</h2>");
        $("#tpmcontent").html("");
        let tabla  = json.tmonths, figs = json.tmonths.length, sum = 0;
        for(let i in tabla){
            sum += Number(tabla[i]["precio"]);
            $("#tpmcontent").append("<tr><td>"+tabla[i]["serial"]+"</td><td>"+capEachWord(tabla[i]["figura"])+"</td>"+
                                        "<td style='text-align:right'>"+formMoney(Number(tabla[i]["precio"]))+"</td>"+
                                        "<td>"+tabla[i]["dia"]+" de "+month[tabla[i]["mes"]]+"</td></tr>");
        }
        $("#tpmcontent").append("<tr><td>-</td><td>Total</td><td style='text-align:right'>"+formMoney(sum)+"</td><td>"+figs+" figuras</td></tr>");
    }).fail(function(xhr, status, err) {
        console.log('Error al eviar: '+xhr.responseText+'\nError: '+status+"\nStatus: "+xhr.status);
    });
}());




