import React, { useState } from 'react'

import "./LoginRegister.css"
import PatientLogin from './PatientLogin';
import DoctorLogin from './DoctorLogin';
import HospitalLogin from './HospitalLogin';

const LoginRegister = () => {
    const [user, setuser] = useState(1);
    const [whatto, setwhatto] = useState(1);
    const [otpSend, setotpSend] = useState(0);
  return (
    <div className='Container'>
        <div className='Navigation'>
            <button onClick={()=>{setuser(1);setotpSend(0);setwhatto(1)}} disabled={user===1}>Patient</button>
            <button onClick={()=>{setuser(2);setotpSend(0);setwhatto(1)}} disabled={user===2}>Doctor</button>
            <button onClick={()=>{setuser(3);setotpSend(0);setwhatto(1)}} disabled={user===3}>Hospital</button>
        </div>
            {user===1 && <PatientLogin/>}
            {user===2 && <DoctorLogin/> }
            {user===3 && <HospitalLogin/>}
    </div>
  )
}

export default LoginRegister