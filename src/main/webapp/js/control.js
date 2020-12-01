'use strict';

function init(){
    $("#nClassGroup").hide();
    $("#nLugarGroup").hide();
    $("#movieGroup").hide();
    $("#clase").val('Deluxe');
    
    
    
    let date = new Date(), prfx;
    let day = ("0" + date.getDate()).slice(-2),
        month = ("0" + (date.getMonth() + 1)).slice(-2);

    $("#fecha").val(date.getFullYear()+'-'+month+'-'+day);


    $.ajax({
            url: 'rservices/figs/presets',
            method: 'GET',
            dataType:'json'
    })
    .done(function(json){
        /*Setting data obtained*/
        console.log('presets:'+JSON.stringify(json));
        json['SHORT'].push('Studio Series');
        json['PREFIX'].push('SS-');
        json['ID_DET'].push('-1');
        $("#serie").html(getSelector(json['SHORT'], json['ID_DET']));
        $("#serie").append("<option value='0'>Otra</option>");
        $("#movie").html(getSelector(json['SERIE'], json['ID']));
        $("#lugar").html(getSelector(json['PLACE'], json['PLACE']));
        $("#serie").append("<option value='Other'>Otro</option>");
        prfx = [json['ID_DET'], json['PREFIX']];
    });
    
    $("#clase").on('change', function(evt){
        if($("#clase").val()==='Other'){
            $("#nClassGroup").show();
            $("#claseC").attr('name', 'clase');
            
        }
        else{
            $("#nClassGroup").hide();
            $("#claseC").attr('name', '');
        }
    });
    
    $("#serie").on('change', function(evt){
        $("#serial").val(prfx[1][prfx[0].indexOf($("#serie").val())]);
        if($("#serie").val()==='-1'){
            $("#movieGroup").show();
            $("#movie").attr('name', 'serie');
        }
        else{
            $("#movieGroup").hide();
            $("#movie").attr('name', '');
        }
    });
    
    $("#lugar").on('change', function(evt){
        if($("#lugar").val()==='Other'){
            $("#nLugarGroup").show();
            $("#lugarC").attr('name', 'lugar');
            
        }
        else{
            $("#nLugarGroup").hide();
            $("#lugarC").attr('name', '');
        }
    });
    
    $("#imagen").on('change', function(){
        if (this.files && this.files[0]) {
            console.log(URL.createObjectURL(this.files[0]));
            $('#myImg').attr('src', URL.createObjectURL(this.files[0]));
            $('#myImg').on('load', imageIsLoaded);
        }
    });

    function imageIsLoaded(){ 
        console.log(this.src);
    }
};

$("#addForm").submit(
    function(evt){
        evt.preventDefault();
        let data = getFromData($("#addForm"));
        console.log(data);
        $.ajax({
            url: 'rservices/figs',
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            dataType:'json',
            data: JSON.stringify(data),
            success: function(json){
                notie.alert({type: 1, text:'<br><br>La figura '+json['figura']+' ha sido agregada exitosamente!', time: 3});
                $("#addForm").trigger('reset');
            }
        })
        .fail(
            function(xhr, status, err) {
                notie.alert({type: 3, text:'Error: No se pudo agregar la figura.', time: 3});
                console.log('Error al eviar: '+xhr.responseText+'\nError: '+status+"\nStatus: "+xhr.status);
        });
        //notie.alert({ type: 1, text: '<br><br>Success!', time: 3}); 
    }
);


function getFromData(frm){
    var unindexed_array = frm.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    return indexed_array;
}

function getSelector(names, values){
    let res = "";
    for (var i = 0; i < names.length; i++) {
        res += "<option value='"+values[i]+"'>"+names[i]+"</option>\n";
    }
    return res;
}

init();