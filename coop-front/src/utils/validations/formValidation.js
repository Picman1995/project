export function validateEmail(username){
        //const regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        const regex = /^[A-Za-z0-9]/;
        return regex.test(username)
}

export function validatePassword(password){
        const regex = /^[A-Za-z\d]{5,15}$/;
        return regex.test(password);
}