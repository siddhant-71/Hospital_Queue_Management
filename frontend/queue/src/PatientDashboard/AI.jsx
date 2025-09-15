import React, { useState } from 'react'
import PatientBody from './PatientBody'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const AI = () => {
    const navigate=useNavigate();
    const [response, setresponse] = useState("ENTER WHATS HAPPENING TO YOU AND ASK AI");
    const [inpot, setinpot] = useState("")
    async function askAI() {
        const resp=await axios.get(`${BackendUrl}/ai/${inpot}`);
        setresponse(resp.data)
    }
    function goBack(){
        navigate('/Patient');
    }
  return (
    <div className='PatientDashboard'>
        <div className='Header'>
            <p>{localStorage.getItem("name")} </p>
            <p>{localStorage.getItem("email")}</p>
            <p>{localStorage.getItem("phoneNo")}</p>
            <p>{localStorage.getItem("age")}</p>
            <p>{localStorage.getItem("bloodGroup")}</p>
            <button>LogOut</button>
        </div>
        <div>
            <button className='back' onClick={goBack}>Go Back</button>
            <input className='inp' type="text" name="" id="" placeholder='ENTER THE PROBLEM' value={inpot} onChange={(e)=>setinpot(e.target.value)}/>
            <button className='ask' onClick={askAI}>ASK AI</button>
        </div>
        <div className='res'>
            {response}
        </div>
    </div>
  )
}

export default AI