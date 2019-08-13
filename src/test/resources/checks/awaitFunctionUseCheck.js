function run1() {// Noncompliant 
	await waitFiles();
}

function run2(){
	waitFiles(function (){// Noncompliant 
		await doSomeThing();
	});
}

async function run3(){// Noncompliant 
	waitFiles(function (){
		await doSomeThing();
	});
}

async function run4() {// OK 
	await waitFiles();
}

function run5(){
	waitFiles(async function (){// OK 
		await doSomeThing();
	});
}
