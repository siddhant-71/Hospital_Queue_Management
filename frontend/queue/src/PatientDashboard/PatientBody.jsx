import React, { useState } from 'react'
import axios from 'axios'
import ViewHospital from './ViewHospital';
import Doctors from './Doctors';
import SlotDoctor from './SlotDoctor';
import DetailDoctor from './DetailDoctor';
import { useNavigate } from 'react-router-dom';
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const PatientBody = () => {
    const navigate=useNavigate();
    const [searched, setsearched] = useState(0);
    const [input, setinput] = useState("");
    const [hospitalData, sethospitalData] = useState([]);
    const [doctorsData, setdoctorsData] = useState([]);
    const [slot, setslot] = useState([])
    const [individual , setindividual]=useState([]);
    async function handleSearch() {
        try{
            const response=await axios.get(`${BackendUrl}/hospital/get/${input}`);
            setsearched(1);
            sethospitalData(response.data);
        }
        catch(e){
            console.log(e);
        }
    }
    function goToAi(){
        navigate('/AI');
    }
  return (
    <div className='Content'>
        {searched!==4 && searched!==3 &&
            <div className='ContentOne'>
            <input type="text" placeholder='Search Hospital' value={input} onChange={(e)=>setinput(e.target.value)}/>
            <button className='serbutton' onClick={handleSearch}>search</button>
            <button className='aiBtn' onClick={goToAi}>Enter AI</button>
        </div>}
        { searched===0 &&
        <div className='Loc'>
            <button>GET HOSPITAL NEAR YOU</button>
        </div>}
        {searched===1 && 
            <div>
                <ViewHospital hospitalData={hospitalData} doctorsData={doctorsData} setsearched={setsearched} setdoctorsData={setdoctorsData}/>
            </div>
        }
        {searched===3 && 
            <div>
                <Doctors doctorsData={doctorsData} setslot={setslot} slot={setsearched} setindividual={setindividual}/>
            </div>
        }
        {searched===4 &&
            <div>
                <DetailDoctor individual={individual}/>
                <SlotDoctor setsearched={setsearched} setslot={setslot} slots={slot} individual={individual}/>
            </div>
        }
    </div>
  )
}

export default PatientBody