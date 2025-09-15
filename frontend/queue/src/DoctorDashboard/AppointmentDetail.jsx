import React, { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import DoctorHeader from './DoctorHeader';
import axios from 'axios';
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const AppointmentDetail = () => {
    const location=useLocation();
    const navigate=useNavigate();
    const {appoint}=location.state;
    console.log(appoint);
    let sett=false;
    if(appoint.status==="PENDING")sett=true;
    const patientId=appoint.patientId;
    const doctorId=appoint.doctorId;

    async function handleCancelBtnClick() {

        const response=await axios.post(`${BackendUrl}/appointment/cancel/${appoint.slot}/${appoint.appointmentId}`,
            {
                "patientId":patientId,
                "doctorId":doctorId
            }
        )
        console.log(response.data);
        navigate('/DoctorBody');
    }
    async function handleRejectBtnClick() {

        const response=await axios.post(`${BackendUrl}/appointment/reject/${appoint.slot}/${appoint.appointmentId}`,
            {
                "patientId":patientId,
                "doctorId":doctorId
            }
        )
        console.log(response.data);
        navigate('/DoctorBody');
        
    }
    async function handleDoneBtnClick() {

        const response=await axios.post(`${BackendUrl}/appointment/done/${appoint.slot}/${appoint.appointmentId}`,
            {
                "patientId":patientId,
                "doctorId":doctorId
            }
        )
        console.log(response.data);
        navigate('/DoctorBody');
        
    }
    
  return (
    <>
        <DoctorHeader/>
        <div className='appContainer'>
            <h1>Appointment Id- {appoint.appointmentId}</h1>
            <h2>Appointment Booking Date - {appoint.bookingTime.slice(2,10)}</h2>
            <h2>Appointment Booking Time - {appoint.bookingTime.slice(11,16)}</h2>
            <h1>Status - {appoint.status}</h1>
            {sett &&
                <div>
                    <button className='btnn' onClick={handleDoneBtnClick}>Mark Done</button>
                    <button className='btnn' onClick={handleCancelBtnClick}>Cancel Appointment</button>
                    <button className='btnn' onClick={handleRejectBtnClick}>Reject Appointment</button>
                </div>
            }
        </div>
    </>
  )
}

export default AppointmentDetail