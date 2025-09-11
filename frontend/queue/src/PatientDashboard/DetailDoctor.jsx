import React from 'react'

const DetailDoctor = ({individual}) => {
    console.log(individual)
  return (
    <>
        <div>
            {individual.hospital.name}
        </div>
        <div className='detail'>
            <div>Name</div>
            <div>Specialisation</div>
            <div>Degree</div>
            <div>department</div>
        </div>
    </>
  )
}

export default DetailDoctor