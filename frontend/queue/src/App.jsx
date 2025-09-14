import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import LoginRegister from './Pages/LoginRegister'
import PatientDashboard from './PatientDashboard/PatientDashboard'
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import DoctorBody from './DoctorDashboard/DoctorBody'
import AppointmentDetail from './DoctorDashboard/AppointmentDetail'
import HospitalDashboard from './HospitalDashboard/HospitalDashboard'
import Payment from './PatientDashboard/Payment'
import AI from './PatientDashboard/AI'

function App() {

  return (
    <Router>
      <Routes>
        <Route path='/' element={<LoginRegister/>}></Route>
        <Route path='/Patient' element={<PatientDashboard/>}></Route>
        <Route path='/DoctorBody' element={<DoctorBody/>}></Route>
        <Route path='/Pay' element={<Payment/>}></Route>
        <Route path='/AI' element={<AI/>}></Route>
        <Route path='/Appointment' element={<AppointmentDetail/>}/>
        <Route path='/Hospital' element={<HospitalDashboard/>}/>
      </Routes>
    </Router>
  )
}

export default App