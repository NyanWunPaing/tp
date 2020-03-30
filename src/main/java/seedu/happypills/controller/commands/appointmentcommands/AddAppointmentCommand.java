package seedu.happypills.controller.commands.appointmentcommands;

import seedu.happypills.HappyPills;
import seedu.happypills.model.data.Appointment;
import seedu.happypills.model.data.AppointmentMap;
import seedu.happypills.model.data.PatientMap;
import seedu.happypills.model.data.VisitMap;
import seedu.happypills.model.data.Patient;
import seedu.happypills.model.exception.HappyPillsException;
import seedu.happypills.storage.Storage;
import seedu.happypills.view.ui.TextUi;

import java.io.IOException;
import java.util.logging.Logger;

public class AddAppointmentCommand extends AppointmentCommand {
    protected String nric;
    protected String date;
    protected String time;
    protected String reason;

    Logger logger = Logger.getLogger(HappyPills.class.getName());

    /**
     * Constructor for AddPatientCommand Class.
     * It creates a new AddPatientCommand Object with the information provided.
     *
     * @param nric   patient's nric.
     * @param date   date of appointment.
     * @param time   time of appointment.
     * @param reason reason for appointment.
     */
    public AddAppointmentCommand(String nric, String date, String time, String reason) {
        this.nric = nric;
        this.date = date;
        this.time = time;
        this.reason = reason;
    }

    /**
     * Add appointment into the Appointment list and the patient's appointment list.
     *
     * @param patients     the list of patients.
     * @param appointments the list of appointments.
     * @return the message to be displayed to the user.
     * @throws HappyPillsException throws exception if there is already an appointment at the same datetime.
     */
    @Override
    public String execute(
            PatientMap patients, AppointmentMap appointments, VisitMap visits
    ) throws HappyPillsException {
        String message = "";
        if (!patients.containsKey(nric)) {
            message = "    Patient does not exist. Please try again.\n"
                    + TextUi.DIVIDER;
        } else {
            Appointment appointment = new Appointment(nric, reason, date, time);
            appointments.addAppointment(appointment);
            Patient patient = (Patient) patients.get(nric);
            patient.addAppointment(appointment);
            try {
                Storage.addSingleItemToFile(Storage.APPOINTMENT_FILEPATH, appointment.toSave());
            } catch (IOException e) {
                logger.warning("Patient's appointment not added to file.");
            }
            message = "    Appointment has been added with Appointment ID "
                    + appointment.getAppointmentId() + ".\n"
                    + TextUi.DIVIDER;
        }
        return message;
    }
}
