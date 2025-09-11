import React, { useState } from 'react'
import axios from 'axios';

const PatientLogin = () => {
    const [whatto, setwhatto] = useState(1);
    const [otpSend, setotpSend] = useState(0);

    const [name, setname] = useState("")
    const [phoneNo, setphoneNo] = useState("")
    const [password, setpassword] = useState("")
    const [email, setemail] = useState("")
    const [age, setage] = useState()
    const [area,setarea]=useState("");
    const [city, setcity] = useState("")
    const [state, setstate] = useState("")
    const [country, setcountry] = useState("")
    const [pincode, setpincode] = useState()
    const [street, setstreet] = useState("")
    const [bloodGrp, setbloodGrp] = useState("")
    const [gender, setgender] = useState("")

    async function handleLogin() {
        try{
            const response=await axios.post(`http://localhost:8080/patient/login`,
                {
                    "input":phoneNo,
                    "password":password
                }
            );
            console.log(response);
            localStorage.setItem('name',response.data.name);
            localStorage.setItem('age',response.data.age);
            localStorage.setItem('email',response.data.email)
            localStorage.setItem('id',response.data.id);
            localStorage.setItem('bloodGroup',response.data.bloodGroup)
            localStorage.setItem('gender',response.data.gender)
            localStorage.setItem('phoneNo',response.data.phoneNumber)


            // NAVIGATE TO DASHBOARD
        }
        catch(e){
            console.log(e);
        }   
        finally{
            setphoneNo("");
            setpassword("")
        }
    }
    async function handleRegister() {
        try{
            const registerResponse=await axios.post(`http://localhost:8080/patient/register`,
                {
                    "name":name,
                    "phoneNumber":phoneNo,
                    "email":email,
                    "password":password,
                    "gender":gender,
                    "bloodGroup":bloodGrp,
                    "age":age,
                    "street":street,
                    "area":area,
                    "city":city,
                    "state":state,
                    "country":country,
                    "pincode":pincode
                }
            )
            console.log(registerResponse);
            setwhatto(1);
        }
        catch(e){
            console.log(e);
        }
        finally{
            setphoneNo("");
            setpassword("");
        }
    }
    async function handleSendOtp() {
        try{
            const isSent=await axios.post(`http://localhost:8080/patient/sendOtp/${phoneNo}`);
            if(isSent){
                setotpSend(1);
            }
        }
        catch(e){
            console.log(e);
        }
    }
    async function submitOtp() {
        try{
            const PatientDTO=await axios.post(`http://localhost:8080/patient/loginViaOtp`,
                {
                    "input":phoneNo,
                    "password":password
                }
            )
            console.log(PatientDTO.data);
            localStorage.setItem('name',PatientDTO.data.name);
            localStorage.setItem('age',PatientDTO.data.age);
            localStorage.setItem('email',PatientDTO.data.email)
            localStorage.setItem('id',PatientDTO.data.id);
            localStorage.setItem('bloodGroup',PatientDTO.data.bloodGroup)
            localStorage.setItem('gender',PatientDTO.data.gender)
            localStorage.setItem('phoneNo',PatientDTO.data.phoneNumber)


            // NAVIGATE TO DASHBOARD

        }
        catch(e){
            console.log(e);
        }
    }
  return (
    <div className='Input'>
                    {whatto===1 && <div className='LoginContainer'>
                        <input type="text" name="" id="" placeholder='Enter Your Phone Number' value={phoneNo} onChange={(e)=>setphoneNo(e.target.value)}/>
                        <input type="text" name="" id="" placeholder='Enter Your Password' value={password} onChange={(e)=>setpassword(e.target.value)}/>
                        <button onClick={handleLogin}>Patient Login</button>
                        <div className='subClass'>
                            <p onClick={()=>setwhatto(2)}>Register</p>
                            <p onClick={()=>setwhatto(3)}>Login Via Otp</p>
                        </div>
                    </div>}
                    {whatto===2 && <div className='LoginContainer'>
                        <input type="text" name="" id="" placeholder='Enter Name' value={name} onChange={(e)=>setname(e.target.value)}/>
                        <input type="text" name="" id="" placeholder='Enter Phone No.' value={phoneNo} onChange={(e)=>setphoneNo(e.target.value)}/>
                        <input type="text" name="" id="" placeholder='Enter Email' value={email} onChange={(e)=>setemail(e.target.value)}/>
                        <input type="text" name="" id="" placeholder='Enter Password' value={password} onChange={(e)=>setpassword(e.target.value)}/>
                        <div className='subClass'>
                            <input type="text" name="" id="" placeholder='Age' value={age} onChange={(e)=>setage(e.target.value)}/>
                            <input type="text" name="" id="" placeholder='Gender' value={gender} onChange={(e)=>setgender(e.target.value)}/>
                            <input type="text" name="" id="" placeholder='Blood G' value={bloodGrp} onChange={(e)=>setbloodGrp(e.target.value)}/>
                        </div>
                        <div className='subClass'>
                            <input type="text" name="" id="" placeholder='street' value={street} onChange={(e)=>setstreet(e.target.value)}/>
                            <input type="text" name="" id="" placeholder='area' value={area} onChange={(e)=>setarea(e.target.value)}/>
                            <input type="text" name="" id="" placeholder='city' value={city} onChange={(e)=>setcity(e.target.value)}/>
                        </div>
                        <div className='subClass'>
                            <input type="text" name="" id="" placeholder='state' value={state} onChange={(e)=>setstate(e.target.value)}/>
                            <input type="text" name="" id="" placeholder='country' value={country} onChange={(e)=>setcountry(e.target.value)}/>
                            <input type="text" name="" id="" placeholder='pincode' value={pincode} onChange={(e)=>setpincode(e.target.value)}/>
                        </div>
                        <button onClick={handleRegister}>Patient Register</button>
                        <div className='subClass'>
                            <p onClick={()=>{setwhatto(1);setotpSend(0)}}>Login</p>
                            <p onClick={()=>{setwhatto(3);setotpSend(0)}}>Login Via Otp</p>
                        </div>
                    </div>}
                    {whatto===3 && <div className='LoginContainer'>
                        {otpSend===0 && <input type="text" name="" id="" placeholder='Enter Your Phone No.' value={phoneNo} onChange={(e)=>setphoneNo(e.target.value)}/>}
                        {otpSend===0 && <button onClick={handleSendOtp}>Patient SendOtp</button>}
                        {otpSend===1 && <input type="text" name="" id="" placeholder='Enter the OTP' value={password} onChange={(e)=>setpassword(e.target.value)}/>}
                        {otpSend===1 && <button onClick={submitOtp}>Submit OTP</button>}

                        <div className='subClass'>
                            <p onClick={()=>{setwhatto(1);setotpSend(0)}}>Login Via Password</p>
                            <p onClick={()=>{setwhatto(2);setotpSend(0)}}>Register</p>
                        </div>
                    </div>}
                </div>
  )
}

export default PatientLogin