import React, { useState } from 'react'
import axios from 'axios'
import { useNavigate} from 'react-router-dom';

const DoctorLogin = () => {
    const navigate=useNavigate();
    const [phoneNo, setphoneNo] = useState("");
    const [enteredOtp, setenteredOtp] = useState("");
    const [password, setpassword] = useState("");
    const [whatto, setwhatto] = useState(1);
    const [otpSend, setotpSend] = useState(0);
    const [name, setname] = useState("");
    const [email, setemail] = useState("");
    const [age, setage] = useState();
    const [gender, setgender] = useState("");
    const [street, setstreet] = useState("");
    const [area, setarea] = useState("");
    const [city, setcity] = useState("");
    const [state, setstate] = useState("");
    const [country, setcountry] = useState("");
    const [pincode, setpincode] = useState();
    const [hospitalId, sethospitalId] = useState("");
    const [Department, setDepartment] = useState("");
    const [Specialization, setSpecialization] = useState("");
    const [degrees, setdegrees] = useState("");
    const handleLogin = async (e) => {
        setphoneNo("");
        setpassword("");
    e.preventDefault();
    try {
      const response1 = await axios.post(`http://localhost:8080/doctor/login`, {
        "input":phoneNo,
        "password":password,
      });
      console.log("Login Successful");
      console.log(response1.data);
      let response=response1.data;
      localStorage.setItem('id',response.id);
      localStorage.setItem('age',response.age);
      localStorage.setItem('area',response.area);
      localStorage.setItem('city',response.city);
      localStorage.setItem('country',response.country);
      localStorage.setItem('degrees',response.degrees);
      localStorage.setItem('department',response.department);
      localStorage.setItem('email',response.email);
      localStorage.setItem('gender',response.gender);
      localStorage.setItem('name',response.name);
      localStorage.setItem('phoneNumber',response.phoneNo);
      localStorage.setItem('pincode',response.pincode);
      localStorage.setItem('specialization',response.specialization)
      localStorage.setItem('state',response.state);
      localStorage.setItem('street',response.street)
      localStorage.setItem('slots',response.slots);
      localStorage.setItem('hospitalName',response.hospital.name);
      localStorage.setItem('hospitalArea',response.hospital.area);
      localStorage.setItem('hospitalCity',response.hospital.city);
      localStorage.setItem('hospitalCountry',response.hospital.country);
      localStorage.setItem('hospitalId',response.hospital.id);
      localStorage.setItem('hospitalPincode',response.hospital.pincode);
      localStorage.setItem('hospitalState',response.hospital.state);
      localStorage.setItem('hospitalStreet',response.hospital.street);
      console.log(localStorage.getItem('hospitalName'))
      console.log(localStorage.getItem('hospitalArea'))
      console.log(localStorage.getItem('hospitalCity'))
      console.log(localStorage.getItem('hospitalCountry'))
      console.log(localStorage.getItem('hospitalPincode'))
      console.log(localStorage.getItem('hospitalId'))
      console.log(localStorage.getItem('hospitalState'))
        navigate("/DoctorBody");
    }
    catch (error) {
        console.error("Login failed:", error.response?.data?.message || error.message);
        alert("Invalid username or password!");
    }
    finally{
        setphoneNo("");
        setpassword("");
    }
    };
    const handleRegister = async(e) =>{
        e.preventDefault();
        try{
            const registerResponse=await axios.post(`http://localhost:8080/doctor/register`,{
                "name":name,
                "specialization":Specialization,
                "hospitalId":hospitalId,
                "department":Department,
                "degrees":degrees,
                "phoneNumber":phoneNo,
                "email":email,
                "password":password,
                "gender":gender,
                "age":age,
                "street":street,
                "area":area,
                "city":city,
                "state":state,
                "country":country,
                "pincode":pincode
            })
            console.log(registerResponse);
            setwhatto(1);
        }
        catch(e){
            console.log(e);
        }
    }
    const handleSendOtp = async(e)=>{
        e.preventDefault();
        try{
            const sendOtpResponse=await axios.post(`http://localhost:8080/doctor/sendOtp/${phoneNo}`);
            if(sendOtpResponse){
                setotpSend(1);
            }
        }
        catch(e){
            console.log(e);
        }
    }
    const handleLoginViaOtp=async(e)=>{
        e.preventDefault();
        try{
            const res=await axios.post(`http://localhost:8080/doctor/loginViaOtp`,
                {
                    "input":phoneNo,
                    "password":enteredOtp
                }
            )
            console.log(res);
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
                        <button onClick={handleLogin}>Doctor Login</button>
                        <div className='subClass'>
                            <p onClick={()=>setwhatto(2)}>Register</p>
                            <p onClick={()=>setwhatto(3)}>Login Via Otp</p>
                        </div>
                    </div>}
                    {whatto===2 && <div className='LoginContainer'>
                        <input type="text" name="" id="" value={name} placeholder='Enter Name' onChange={(e)=>setname(e.target.value)}/>
                        <input type="text" name="" id="" value={phoneNo} placeholder='Enter Phone No.' onChange={(e)=>setphoneNo(e.target.value)}/>
                        <input type="text" name="" id="" value={email} placeholder='Enter Email' onChange={(e)=>setemail(e.target.value)}/>
                        <input type="text" name="" id="" value={password} placeholder='Enter Password' onChange={(e)=>setpassword(e.target.value)}/>
                        <div className='subClass'>
                            <input type="text" name="" id="" value={age} placeholder='Age' onChange={(e)=>setage(e.target.value)}/>
                            <input type="text" name="" id="" value={gender} placeholder='Gender' onChange={(e)=>setgender(e.target.value)}/>
                        </div>
                        <div className='subClass'>
                            <input type="text" name="" id="" value={street} placeholder='street' onChange={(e)=>setstreet(e.target.value)}/>
                            <input type="text" name="" id="" value={area} placeholder='area' onChange={(e)=>setarea(e.target.value)}/>
                            <input type="text" name="" id="" value={city} placeholder='city' onChange={(e)=>setcity(e.target.value)}/>
                        </div>
                        <div className='subClass'>
                            <input type="text" name="" id="" value={state} placeholder='state' onChange={(e)=>setstate(e.target.value)}/>
                            <input type="text" name="" id="" value={country} placeholder='country' onChange={(e)=>setcountry(e.target.value)}/>
                            <input type="text" name="" id="" value={pincode} placeholder='pincode' onChange={(e)=>setpincode(e.target.value)}/>
                        </div>
                        <input type="text" value={hospitalId}  placeholder='Enter Hospital Id' onChange={(e)=>sethospitalId(e.target.value)}/>
                        <input type="text" value={Department} placeholder='Enter Department' onChange={(e)=>setDepartment(e.target.value)}/>
                        <div className='subClass'>
                            <input type="text" value={Specialization} placeholder='Enter Specialization' onChange={(e)=>setSpecialization(e.target.value)}/>
                            <input type="text" value={degrees} placeholder='Enter Degrees' onChange={(e)=>setdegrees(e.target.value)}/>
                        </div>
                        <button onClick={handleRegister}>Doctor Register</button>
                        <div className='subClass'>
                            <p onClick={()=>{setwhatto(1);setotpSend(0)}}>Login</p>
                            <p onClick={()=>{setwhatto(3);setotpSend(0)}}>Login Via Otp</p>
                        </div>
                    </div>}
                    {whatto===3 && <div className='LoginContainer'>
                        {otpSend===0 && <input type="text" name="" id="" value={phoneNo} onChange={(e)=>setphoneNo(e.target.value)} placeholder='Enter Your Phone No.'/>}
                        {otpSend===0 && <button onClick={handleSendOtp}>Doctor SendOtp</button>}
                        {otpSend===1 && <input type="text" name="" id="" value={enteredOtp} onChange={(e)=>setenteredOtp(e.target.value)} placeholder='Enter the OTP'/>}
                        {otpSend===1 && <button onClick={handleLoginViaOtp}>Submit OTP</button>}

                        <div className='subClass'>
                            <p onClick={()=>{setwhatto(1);setotpSend(0)}}>Login Via Password</p>
                            <p onClick={()=>{setwhatto(2);setotpSend(0)}}>Register</p>
                        </div>
                    </div>}
                </div>
  )
}

export default DoctorLogin