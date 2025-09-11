import axios from 'axios';
import React from 'react'

const HospitalCard = ({card,doctorsData,setsearched,setdoctorsData}) => {
    async function handleOpen() {
        try{
                const OneHospital=await axios.post(`http://localhost:8080/hospital/doctors`,{
                "input":card.id,
                "password":card.password
                })  
                setdoctorsData(OneHospital.data);
                setsearched(3);
            }   
        catch(e){
            console.log(e);
        }   
    }
  return (
    <button className='Card' onClick={handleOpen}>
        <div>Name</div>
        <div>Street , Area</div>
        <div>City , Pincode</div>
        <div>State , Country</div>
    </button>
  )
}

export default HospitalCard