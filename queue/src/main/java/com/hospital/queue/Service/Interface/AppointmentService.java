package com.hospital.queue.Service.Interface;

import com.hospital.queue.DTO.AppointmentBookedResponse;
import com.hospital.queue.Entities.Appointment;

public interface AppointmentService {
    AppointmentBookedResponse bookAppointment(Long doctorId, Long patientId, int slot);
    boolean cancelAppointment(Long doctorId, Long patientId, int slot,Long id,int i);
}
