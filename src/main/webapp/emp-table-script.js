console.log('hello')

//grab the table elemenet from the page
//we can modify how it looks and add elements 

let table  = document.querySelector('table');
//this save the table element to the variable 

let button = document.getElementById('all-emps')
//when the button is clciked we 
// make a call to the server, fetch the json data, 
//parse json data, and append to table 

//button.addEventListener("click", sayHello);


button.addEventListener('click', fetchEmps)
function sayHello(){
    console.log('hello there')
}

function fetchEmps(){
    //fetch API is a modern interface that allows you 
    // to make HTTP requests to a server 
    // and process the resiults you get back asynchronously 

    let hostname = window.location.hostname // tyhis will grab IP of where it's deployed (localhost, or Live server )
    //this is the url that retrieves the employee list with a template literal
    //EDIT: we must remove the portname because when it's deployed it wont need this bc port is already inferred when deploying
    fetch(`http://${hostname}/employee-servlet-app/employees`)
    .then(response => response.json() ) // takes a json string 
                                        //and trnasforms it to Javascript object
    //.then(obj => console.log(obj));//then print       
      .then(buildTable);//automatically passes the data that's been parsed (the js object)                         

                        

}

function buildTable(data){
    console.log('build table method has been triggered');
    console.log(data);

    let header = document.createElement('thead'); // t head element: HTML elemet 

    let headerRow = document.createElement('tr');// tr is an HTML element 

    header.appendChild(headerRow);

    //we need to append the header(which appended the header row) to the table  we created above 
    table.appendChild(header);

    //create a header column for first name
    let th1 = document.createElement('th')
    th1.innerHTML = 'first name';

    //last name
    let th2 = document.createElement('th')
    th2.innerHTML = 'last name';

    //username 
    let th3 = document.createElement('th')
    th3.innerHTML = 'username';

    //append the child nodes to the header 
    headerRow.appendChild(th1);
    headerRow.appendChild(th2);
    headerRow.appendChild(th3);

    //for each element in datra, console log it 
    data.forEach(element => {
        console.log(element);

        let row = document.createElement('tr'); // tr = table row 
        let td1 = document.createElement('td');//table data 1 
        let td2 = document.createElement('td');//table data 1 
        let td3 = document.createElement('td');//table data 1 

        //set inner html of eachc ell to the diff properties of a user (first name, last, user)
        td1.innerHTML = element.firstName;
        td2.innerHTML = element.lastName; 
        td3.innerHTML = element.username;

        //finally, append each table call to the row 
        row.appendChild(td1);
        row.appendChild(td2);
        row.appendChild(td3);

        //append row to the table 
        table.appendChild(row);

    });




}

