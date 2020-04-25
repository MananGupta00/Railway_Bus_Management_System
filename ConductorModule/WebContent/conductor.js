
var city_name =["city_1","city_2","city_3","city_4"];
var n = 0;
var permit = false;

function fun(n) {
    let str = "";
    for(var i = 0;i < city_name.length;i++) {
        if(i<=n)
        str = city_name[i].fontcolor("Green");
        else
        str = city_name[i].fontcolor("Red");

        document.getElementById("root").innerHTML += str;
    }
    console.log(document.getElementById("root").innerText);
    //yha pr old name hta kr new name print ni ho rhe h add up ho rhe h and ye greeen bhi sath m nhi ho rhe h psarticular city name hi ho rhi h   
}

function fun1() {
    if(permit) {
   var cn = document.getElementById("cities").value; 
   console.log(cn);
   for(var i=0;i<city_name.length;i++) {
        if(cn === city_name[i])
        n=i;
        console.log(n);
   }
   fun(n);
    }
    else {
        console.log("Invalid");
    }
}
