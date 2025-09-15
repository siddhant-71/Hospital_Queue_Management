import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const HospitalLogin = () => {
    const navigate=useNavigate();
    const [whatto, setwhatto] = useState(1);
    const [otpSend, setotpSend] = useState(0);
    const [id, setid] = useState(0);
    const [name, setname] = useState("")
    const [street, setstreet] = useState("")
    const [area, setarea] = useState("")
    const [city, setcity] = useState("")
    const [state, setstate] = useState("")
    const [country, setcountry] = useState("")
    const [pincode, setpincode] = useState(0)
    const [password, setpassword] = useState("")

    async function handleLogin() {
        try{
            const response=await axios.post(`${BackendUrl}/hospital/doctors`,{
                "input":id,
                "password":password
            })
            navigate('/Hospital',{state:{doctors:response.data}})
            // THIS IS THE DOCTOR LIST SHOW IT IN THE DISPLAY
        }
        catch(e){
            console.log(e);
        }
        
    }
    async function registerHospital() {
        try{
            const response=await axios.post(`${BackendUrl}/hospital/add`,
                {
                    "name":name,
                    "password":password,
                    "street":street,
                    "area":area,
                    "city":city,
                    "state":state,
                    "country":country,
                    "pincode":pincode
                }
            )
            console.log(response);
            setwhatto(1);
        }
        catch(e){
            console.log(e);
        }
    }
  return (
    <div className='Input'>
                    {whatto===1 && <div className='LoginContainer'>
                        <input type="text" name="" id="" placeholder='Enter Your Id' value={id} onChange={(e)=>{setid(e.target.value)}}/>
                        <input type="text" name="" id="" placeholder='Enter Your Password' value={password} onChange={(e)=>{setpassword(e.target.value)}}/>
                        <button onClick={handleLogin}>Hospital Login</button>
                            <p className='subClassp' onClick={()=>setwhatto(2)}>Register</p>
                    </div>}
                    {whatto===2 && <div className='LoginContainer'>
                        <input type="text" name="" id="" placeholder='Enter Name' value={name} onChange={(e)=>{setname(e.target.value)}}/>
                        <input type="text" name="" id="" placeholder='Enter Password' value={password} onChange={(e)=>{setpassword(e.target.value)}}/>
                        <div className='subClass'>
                            <input type="text" name="" id="" placeholder='street' value={street} onChange={(e)=>{setstreet(e.target.value)}}/>
                            <input type="text" name="" id="" placeholder='area' value={area} onChange={(e)=>{setarea(e.target.value)}}/>
                            <input type="text" name="" id="" placeholder='city' value={city} onChange={(e)=>{setcity(e.target.value)}}/>
                        </div>
                        <div className='subClass'>
                            <input type="text" name="" id="" placeholder='state' value={state} onChange={(e)=>{setstate(e.target.value)}}/>
                            <input type="text" name="" id="" placeholder='country' value={country} onChange={(e)=>{setcountry(e.target.value)}}/>
                            <input type="text" name="" id="" placeholder='pincode' value={pincode} onChange={(e)=>{setpincode(e.target.value)}}/>
                        </div>
                        <button onClick={registerHospital}>Hospital Register</button>
                        <p className='subClassp' onClick={()=>{setwhatto(1);setotpSend(0)}}>Login</p>
                    </div>}
                </div>
  )
}

export default HospitalLogin