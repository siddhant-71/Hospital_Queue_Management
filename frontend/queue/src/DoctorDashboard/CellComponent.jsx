import axios from 'axios';
import React from 'react'
import { useNavigate } from 'react-router-dom'

const CellComponent = ({i,no,classNa}) => {
    const navigate=useNavigate();
    let day='';
    if(i/8>=0 && i/8<1)day="SUNDAY"
    else if(i/8>=1 && i/8<2)day="MONDAY"
    else if(i/8>=2 && i/8<3)day="TUESDAY"
    else if(i/8>=3 && i/8<4)day="WEDNESDAY"
    else if(i/8>=4 && i/8<5)day="THURSDAY"
    else if(i/8>=5 && i/8<6)day="FRIDAY"
    else if(i/8>=6 && i/8<8)day="SATURDAY"
    let time='';
    if(i%8==0)time='9-10 am'
    else if(i%8==1)time='10-11 am'
    else if(i%8==2)time='11-12 noon'
    else if(i%8==3)time='12-1 pm'
    else if(i%8==4)time='1-2 pm'
    else if(i%8==5)time='2-3 pm'
    else if(i%8==6)time='3-4 pm'
    else if(i%8==7)time='4-5 pm'
    async function handleOpenApp() {
        const response=await axios.post(`http://localhost:8080/appointment/get/${no}`);
        console.log(response.data);
        if(response.data.status === 'REJECTED' || response.data.status === 'CANCELLED'){
            alert("Appointment already rejected or Cancelled");
        }
        navigate('/Appointment' ,{state :{ appoint: response.data}});
    }
  return (
    <div className={classNa} onClick={handleOpenApp}>
        {day}
        <hr/>
        {time}
        <hr/>
        {no}
    </div>
  )
}

export default CellComponent