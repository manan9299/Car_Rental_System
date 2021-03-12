
var validateFields = (object) => {
    
    
    var validatePassword = new Promise((resolve,reject)=>{
        if (object.password !== object.confirmpassword)
        {             
            reject(false);
        }
        else{

            resolve(true);
            
        }
    });

    var validateEmail = new Promise((resolve,reject)=>{

        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if(re.test(object.email.toLowerCase()))
        {
            resolve(true);
        }
        else{
            reject(false);
        }
        
    });
    var res = false;
    Promise.all([validateEmail,validatePassword]).then(
        (results) => 
        {
            res = results;

        }
    ).catch(
        (errors)=>
        {
            res = errors;
        }
    )

    return res;        
}

export default{validateFields}
