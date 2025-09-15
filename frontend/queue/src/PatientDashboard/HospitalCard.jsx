import axios from 'axios';
import React from 'react'
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const HospitalCard = ({card,doctorsData,setsearched,setdoctorsData}) => {
    async function handleOpen() {
        try{
                const OneHospital=await axios.post(`${BackendUrl}/hospital/doctors`,{
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
        <div>{card.name}</div>
        <div>{card.street} ,{card.area}</div>
        <div>{card.city} , {card.pincode}</div>
        <div>{card.state} , {card.country}</div>
    </button>
  )
}

export default HospitalCard