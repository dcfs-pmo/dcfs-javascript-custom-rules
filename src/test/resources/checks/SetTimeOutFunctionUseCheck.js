
function myFunc() {
	doSomeThing();
	
}

//Noncompliant 
//反面示例
setInterval("myFunc()", 1000); 
setInterval("myFunc(1, 2, 3)", 1000); 
setTimeout("myFunc()", 1000); 
setTimeout("myFunc(1, 2, 3)", 1000); 

// Ok
setTimeout(myFunc, 1000); 
setTimeout(function () { myFunc(1, 2, 3); }, 1000);
setInterval(myFunc, 1000); 
setInterval(function () { myFunc(1, 2, 3); }, 1000);


