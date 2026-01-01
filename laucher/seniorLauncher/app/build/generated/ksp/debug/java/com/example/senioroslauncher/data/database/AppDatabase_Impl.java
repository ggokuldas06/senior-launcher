package com.example.senioroslauncher.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.senioroslauncher.data.database.dao.AlertDao;
import com.example.senioroslauncher.data.database.dao.AlertDao_Impl;
import com.example.senioroslauncher.data.database.dao.AppointmentDao;
import com.example.senioroslauncher.data.database.dao.AppointmentDao_Impl;
import com.example.senioroslauncher.data.database.dao.EmergencyContactDao;
import com.example.senioroslauncher.data.database.dao.EmergencyContactDao_Impl;
import com.example.senioroslauncher.data.database.dao.HealthCheckInDao;
import com.example.senioroslauncher.data.database.dao.HealthCheckInDao_Impl;
import com.example.senioroslauncher.data.database.dao.HydrationLogDao;
import com.example.senioroslauncher.data.database.dao.HydrationLogDao_Impl;
import com.example.senioroslauncher.data.database.dao.MedicalProfileDao;
import com.example.senioroslauncher.data.database.dao.MedicalProfileDao_Impl;
import com.example.senioroslauncher.data.database.dao.MedicationDao;
import com.example.senioroslauncher.data.database.dao.MedicationDao_Impl;
import com.example.senioroslauncher.data.database.dao.MedicationLogDao;
import com.example.senioroslauncher.data.database.dao.MedicationLogDao_Impl;
import com.example.senioroslauncher.data.database.dao.MedicationScheduleDao;
import com.example.senioroslauncher.data.database.dao.MedicationScheduleDao_Impl;
import com.example.senioroslauncher.data.database.dao.NoteDao;
import com.example.senioroslauncher.data.database.dao.NoteDao_Impl;
import com.example.senioroslauncher.data.database.dao.PairedGuardianDao;
import com.example.senioroslauncher.data.database.dao.PairedGuardianDao_Impl;
import com.example.senioroslauncher.data.database.dao.SpeedDialContactDao;
import com.example.senioroslauncher.data.database.dao.SpeedDialContactDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile MedicationDao _medicationDao;

  private volatile MedicationScheduleDao _medicationScheduleDao;

  private volatile MedicationLogDao _medicationLogDao;

  private volatile EmergencyContactDao _emergencyContactDao;

  private volatile AppointmentDao _appointmentDao;

  private volatile NoteDao _noteDao;

  private volatile SpeedDialContactDao _speedDialContactDao;

  private volatile MedicalProfileDao _medicalProfileDao;

  private volatile HydrationLogDao _hydrationLogDao;

  private volatile AlertDao _alertDao;

  private volatile HealthCheckInDao _healthCheckInDao;

  private volatile PairedGuardianDao _pairedGuardianDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `medications` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `dosage` TEXT NOT NULL, `frequency` TEXT NOT NULL, `notes` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medication_schedules` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `medicationId` INTEGER NOT NULL, `hour` INTEGER NOT NULL, `minute` INTEGER NOT NULL, `daysOfWeek` TEXT NOT NULL, `isEnabled` INTEGER NOT NULL, FOREIGN KEY(`medicationId`) REFERENCES `medications`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medication_schedules_medicationId` ON `medication_schedules` (`medicationId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medication_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `medicationId` INTEGER NOT NULL, `scheduledTime` INTEGER NOT NULL, `actionTime` INTEGER NOT NULL, `action` TEXT NOT NULL, `notes` TEXT NOT NULL, FOREIGN KEY(`medicationId`) REFERENCES `medications`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medication_logs_medicationId` ON `medication_logs` (`medicationId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `emergency_contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `relationship` TEXT NOT NULL, `isPrimary` INTEGER NOT NULL, `photoUri` TEXT, `sortOrder` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `appointments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `dateTime` INTEGER NOT NULL, `location` TEXT NOT NULL, `description` TEXT NOT NULL, `reminderMinutesBefore` INTEGER NOT NULL, `isReminderEnabled` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `speed_dial_contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `photoUri` TEXT, `position` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medical_profile` (`id` INTEGER NOT NULL, `bloodType` TEXT NOT NULL, `allergies` TEXT NOT NULL, `medicalConditions` TEXT NOT NULL, `emergencyNotes` TEXT NOT NULL, `doctorName` TEXT NOT NULL, `doctorPhone` TEXT NOT NULL, `insuranceInfo` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `hydration_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `glassesCount` INTEGER NOT NULL, `goal` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `alerts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `triggeredAt` INTEGER NOT NULL, `latitude` REAL, `longitude` REAL, `batteryLevel` INTEGER, `resolved` INTEGER NOT NULL, `resolvedAt` INTEGER, `notes` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `health_checkins` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `mood` INTEGER, `painLevel` INTEGER, `sleepQuality` INTEGER, `symptoms` TEXT NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `paired_guardians` (`guardianId` TEXT NOT NULL, `guardianName` TEXT NOT NULL, `pairedAt` INTEGER NOT NULL, PRIMARY KEY(`guardianId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a9763a01db40131ae74c4cd8c9f203d4')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `medications`");
        db.execSQL("DROP TABLE IF EXISTS `medication_schedules`");
        db.execSQL("DROP TABLE IF EXISTS `medication_logs`");
        db.execSQL("DROP TABLE IF EXISTS `emergency_contacts`");
        db.execSQL("DROP TABLE IF EXISTS `appointments`");
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `speed_dial_contacts`");
        db.execSQL("DROP TABLE IF EXISTS `medical_profile`");
        db.execSQL("DROP TABLE IF EXISTS `hydration_logs`");
        db.execSQL("DROP TABLE IF EXISTS `alerts`");
        db.execSQL("DROP TABLE IF EXISTS `health_checkins`");
        db.execSQL("DROP TABLE IF EXISTS `paired_guardians`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsMedications = new HashMap<String, TableInfo.Column>(8);
        _columnsMedications.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("dosage", new TableInfo.Column("dosage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("frequency", new TableInfo.Column("frequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedications = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMedications = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMedications = new TableInfo("medications", _columnsMedications, _foreignKeysMedications, _indicesMedications);
        final TableInfo _existingMedications = TableInfo.read(db, "medications");
        if (!_infoMedications.equals(_existingMedications)) {
          return new RoomOpenHelper.ValidationResult(false, "medications(com.example.senioroslauncher.data.database.entity.MedicationEntity).\n"
                  + " Expected:\n" + _infoMedications + "\n"
                  + " Found:\n" + _existingMedications);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicationSchedules = new HashMap<String, TableInfo.Column>(6);
        _columnsMedicationSchedules.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationSchedules.put("medicationId", new TableInfo.Column("medicationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationSchedules.put("hour", new TableInfo.Column("hour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationSchedules.put("minute", new TableInfo.Column("minute", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationSchedules.put("daysOfWeek", new TableInfo.Column("daysOfWeek", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationSchedules.put("isEnabled", new TableInfo.Column("isEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicationSchedules = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMedicationSchedules.add(new TableInfo.ForeignKey("medications", "CASCADE", "NO ACTION", Arrays.asList("medicationId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMedicationSchedules = new HashSet<TableInfo.Index>(1);
        _indicesMedicationSchedules.add(new TableInfo.Index("index_medication_schedules_medicationId", false, Arrays.asList("medicationId"), Arrays.asList("ASC")));
        final TableInfo _infoMedicationSchedules = new TableInfo("medication_schedules", _columnsMedicationSchedules, _foreignKeysMedicationSchedules, _indicesMedicationSchedules);
        final TableInfo _existingMedicationSchedules = TableInfo.read(db, "medication_schedules");
        if (!_infoMedicationSchedules.equals(_existingMedicationSchedules)) {
          return new RoomOpenHelper.ValidationResult(false, "medication_schedules(com.example.senioroslauncher.data.database.entity.MedicationScheduleEntity).\n"
                  + " Expected:\n" + _infoMedicationSchedules + "\n"
                  + " Found:\n" + _existingMedicationSchedules);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicationLogs = new HashMap<String, TableInfo.Column>(6);
        _columnsMedicationLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("medicationId", new TableInfo.Column("medicationId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("scheduledTime", new TableInfo.Column("scheduledTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("actionTime", new TableInfo.Column("actionTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicationLogs.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicationLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMedicationLogs.add(new TableInfo.ForeignKey("medications", "CASCADE", "NO ACTION", Arrays.asList("medicationId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMedicationLogs = new HashSet<TableInfo.Index>(1);
        _indicesMedicationLogs.add(new TableInfo.Index("index_medication_logs_medicationId", false, Arrays.asList("medicationId"), Arrays.asList("ASC")));
        final TableInfo _infoMedicationLogs = new TableInfo("medication_logs", _columnsMedicationLogs, _foreignKeysMedicationLogs, _indicesMedicationLogs);
        final TableInfo _existingMedicationLogs = TableInfo.read(db, "medication_logs");
        if (!_infoMedicationLogs.equals(_existingMedicationLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "medication_logs(com.example.senioroslauncher.data.database.entity.MedicationLogEntity).\n"
                  + " Expected:\n" + _infoMedicationLogs + "\n"
                  + " Found:\n" + _existingMedicationLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsEmergencyContacts = new HashMap<String, TableInfo.Column>(7);
        _columnsEmergencyContacts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("relationship", new TableInfo.Column("relationship", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("isPrimary", new TableInfo.Column("isPrimary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmergencyContacts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmergencyContacts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmergencyContacts = new TableInfo("emergency_contacts", _columnsEmergencyContacts, _foreignKeysEmergencyContacts, _indicesEmergencyContacts);
        final TableInfo _existingEmergencyContacts = TableInfo.read(db, "emergency_contacts");
        if (!_infoEmergencyContacts.equals(_existingEmergencyContacts)) {
          return new RoomOpenHelper.ValidationResult(false, "emergency_contacts(com.example.senioroslauncher.data.database.entity.EmergencyContactEntity).\n"
                  + " Expected:\n" + _infoEmergencyContacts + "\n"
                  + " Found:\n" + _existingEmergencyContacts);
        }
        final HashMap<String, TableInfo.Column> _columnsAppointments = new HashMap<String, TableInfo.Column>(8);
        _columnsAppointments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("dateTime", new TableInfo.Column("dateTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("reminderMinutesBefore", new TableInfo.Column("reminderMinutesBefore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("isReminderEnabled", new TableInfo.Column("isReminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppointments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppointments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppointments = new TableInfo("appointments", _columnsAppointments, _foreignKeysAppointments, _indicesAppointments);
        final TableInfo _existingAppointments = TableInfo.read(db, "appointments");
        if (!_infoAppointments.equals(_existingAppointments)) {
          return new RoomOpenHelper.ValidationResult(false, "appointments(com.example.senioroslauncher.data.database.entity.AppointmentEntity).\n"
                  + " Expected:\n" + _infoAppointments + "\n"
                  + " Found:\n" + _existingAppointments);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(5);
        _columnsNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.example.senioroslauncher.data.database.entity.NoteEntity).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsSpeedDialContacts = new HashMap<String, TableInfo.Column>(5);
        _columnsSpeedDialContacts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSpeedDialContacts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSpeedDialContacts.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSpeedDialContacts.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSpeedDialContacts.put("position", new TableInfo.Column("position", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSpeedDialContacts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSpeedDialContacts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSpeedDialContacts = new TableInfo("speed_dial_contacts", _columnsSpeedDialContacts, _foreignKeysSpeedDialContacts, _indicesSpeedDialContacts);
        final TableInfo _existingSpeedDialContacts = TableInfo.read(db, "speed_dial_contacts");
        if (!_infoSpeedDialContacts.equals(_existingSpeedDialContacts)) {
          return new RoomOpenHelper.ValidationResult(false, "speed_dial_contacts(com.example.senioroslauncher.data.database.entity.SpeedDialContactEntity).\n"
                  + " Expected:\n" + _infoSpeedDialContacts + "\n"
                  + " Found:\n" + _existingSpeedDialContacts);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicalProfile = new HashMap<String, TableInfo.Column>(9);
        _columnsMedicalProfile.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("bloodType", new TableInfo.Column("bloodType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("allergies", new TableInfo.Column("allergies", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("medicalConditions", new TableInfo.Column("medicalConditions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("emergencyNotes", new TableInfo.Column("emergencyNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("doctorName", new TableInfo.Column("doctorName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("doctorPhone", new TableInfo.Column("doctorPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("insuranceInfo", new TableInfo.Column("insuranceInfo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalProfile.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicalProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMedicalProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMedicalProfile = new TableInfo("medical_profile", _columnsMedicalProfile, _foreignKeysMedicalProfile, _indicesMedicalProfile);
        final TableInfo _existingMedicalProfile = TableInfo.read(db, "medical_profile");
        if (!_infoMedicalProfile.equals(_existingMedicalProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "medical_profile(com.example.senioroslauncher.data.database.entity.MedicalProfileEntity).\n"
                  + " Expected:\n" + _infoMedicalProfile + "\n"
                  + " Found:\n" + _existingMedicalProfile);
        }
        final HashMap<String, TableInfo.Column> _columnsHydrationLogs = new HashMap<String, TableInfo.Column>(4);
        _columnsHydrationLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHydrationLogs.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHydrationLogs.put("glassesCount", new TableInfo.Column("glassesCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHydrationLogs.put("goal", new TableInfo.Column("goal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHydrationLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHydrationLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHydrationLogs = new TableInfo("hydration_logs", _columnsHydrationLogs, _foreignKeysHydrationLogs, _indicesHydrationLogs);
        final TableInfo _existingHydrationLogs = TableInfo.read(db, "hydration_logs");
        if (!_infoHydrationLogs.equals(_existingHydrationLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "hydration_logs(com.example.senioroslauncher.data.database.entity.HydrationLogEntity).\n"
                  + " Expected:\n" + _infoHydrationLogs + "\n"
                  + " Found:\n" + _existingHydrationLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsAlerts = new HashMap<String, TableInfo.Column>(9);
        _columnsAlerts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("triggeredAt", new TableInfo.Column("triggeredAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("batteryLevel", new TableInfo.Column("batteryLevel", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("resolved", new TableInfo.Column("resolved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("resolvedAt", new TableInfo.Column("resolvedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlerts.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlerts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlerts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAlerts = new TableInfo("alerts", _columnsAlerts, _foreignKeysAlerts, _indicesAlerts);
        final TableInfo _existingAlerts = TableInfo.read(db, "alerts");
        if (!_infoAlerts.equals(_existingAlerts)) {
          return new RoomOpenHelper.ValidationResult(false, "alerts(com.example.senioroslauncher.data.database.entity.AlertEntity).\n"
                  + " Expected:\n" + _infoAlerts + "\n"
                  + " Found:\n" + _existingAlerts);
        }
        final HashMap<String, TableInfo.Column> _columnsHealthCheckins = new HashMap<String, TableInfo.Column>(8);
        _columnsHealthCheckins.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("mood", new TableInfo.Column("mood", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("painLevel", new TableInfo.Column("painLevel", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("sleepQuality", new TableInfo.Column("sleepQuality", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("symptoms", new TableInfo.Column("symptoms", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthCheckins.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHealthCheckins = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHealthCheckins = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHealthCheckins = new TableInfo("health_checkins", _columnsHealthCheckins, _foreignKeysHealthCheckins, _indicesHealthCheckins);
        final TableInfo _existingHealthCheckins = TableInfo.read(db, "health_checkins");
        if (!_infoHealthCheckins.equals(_existingHealthCheckins)) {
          return new RoomOpenHelper.ValidationResult(false, "health_checkins(com.example.senioroslauncher.data.database.entity.HealthCheckInEntity).\n"
                  + " Expected:\n" + _infoHealthCheckins + "\n"
                  + " Found:\n" + _existingHealthCheckins);
        }
        final HashMap<String, TableInfo.Column> _columnsPairedGuardians = new HashMap<String, TableInfo.Column>(3);
        _columnsPairedGuardians.put("guardianId", new TableInfo.Column("guardianId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPairedGuardians.put("guardianName", new TableInfo.Column("guardianName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPairedGuardians.put("pairedAt", new TableInfo.Column("pairedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPairedGuardians = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPairedGuardians = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPairedGuardians = new TableInfo("paired_guardians", _columnsPairedGuardians, _foreignKeysPairedGuardians, _indicesPairedGuardians);
        final TableInfo _existingPairedGuardians = TableInfo.read(db, "paired_guardians");
        if (!_infoPairedGuardians.equals(_existingPairedGuardians)) {
          return new RoomOpenHelper.ValidationResult(false, "paired_guardians(com.example.senioroslauncher.data.database.entity.PairedGuardianEntity).\n"
                  + " Expected:\n" + _infoPairedGuardians + "\n"
                  + " Found:\n" + _existingPairedGuardians);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a9763a01db40131ae74c4cd8c9f203d4", "e7367b0642cdc0e05dc4692f987cc599");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "medications","medication_schedules","medication_logs","emergency_contacts","appointments","notes","speed_dial_contacts","medical_profile","hydration_logs","alerts","health_checkins","paired_guardians");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `medications`");
      _db.execSQL("DELETE FROM `medication_schedules`");
      _db.execSQL("DELETE FROM `medication_logs`");
      _db.execSQL("DELETE FROM `emergency_contacts`");
      _db.execSQL("DELETE FROM `appointments`");
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `speed_dial_contacts`");
      _db.execSQL("DELETE FROM `medical_profile`");
      _db.execSQL("DELETE FROM `hydration_logs`");
      _db.execSQL("DELETE FROM `alerts`");
      _db.execSQL("DELETE FROM `health_checkins`");
      _db.execSQL("DELETE FROM `paired_guardians`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MedicationDao.class, MedicationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationScheduleDao.class, MedicationScheduleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationLogDao.class, MedicationLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EmergencyContactDao.class, EmergencyContactDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppointmentDao.class, AppointmentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SpeedDialContactDao.class, SpeedDialContactDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicalProfileDao.class, MedicalProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HydrationLogDao.class, HydrationLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AlertDao.class, AlertDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HealthCheckInDao.class, HealthCheckInDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PairedGuardianDao.class, PairedGuardianDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MedicationDao medicationDao() {
    if (_medicationDao != null) {
      return _medicationDao;
    } else {
      synchronized(this) {
        if(_medicationDao == null) {
          _medicationDao = new MedicationDao_Impl(this);
        }
        return _medicationDao;
      }
    }
  }

  @Override
  public MedicationScheduleDao medicationScheduleDao() {
    if (_medicationScheduleDao != null) {
      return _medicationScheduleDao;
    } else {
      synchronized(this) {
        if(_medicationScheduleDao == null) {
          _medicationScheduleDao = new MedicationScheduleDao_Impl(this);
        }
        return _medicationScheduleDao;
      }
    }
  }

  @Override
  public MedicationLogDao medicationLogDao() {
    if (_medicationLogDao != null) {
      return _medicationLogDao;
    } else {
      synchronized(this) {
        if(_medicationLogDao == null) {
          _medicationLogDao = new MedicationLogDao_Impl(this);
        }
        return _medicationLogDao;
      }
    }
  }

  @Override
  public EmergencyContactDao emergencyContactDao() {
    if (_emergencyContactDao != null) {
      return _emergencyContactDao;
    } else {
      synchronized(this) {
        if(_emergencyContactDao == null) {
          _emergencyContactDao = new EmergencyContactDao_Impl(this);
        }
        return _emergencyContactDao;
      }
    }
  }

  @Override
  public AppointmentDao appointmentDao() {
    if (_appointmentDao != null) {
      return _appointmentDao;
    } else {
      synchronized(this) {
        if(_appointmentDao == null) {
          _appointmentDao = new AppointmentDao_Impl(this);
        }
        return _appointmentDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public SpeedDialContactDao speedDialContactDao() {
    if (_speedDialContactDao != null) {
      return _speedDialContactDao;
    } else {
      synchronized(this) {
        if(_speedDialContactDao == null) {
          _speedDialContactDao = new SpeedDialContactDao_Impl(this);
        }
        return _speedDialContactDao;
      }
    }
  }

  @Override
  public MedicalProfileDao medicalProfileDao() {
    if (_medicalProfileDao != null) {
      return _medicalProfileDao;
    } else {
      synchronized(this) {
        if(_medicalProfileDao == null) {
          _medicalProfileDao = new MedicalProfileDao_Impl(this);
        }
        return _medicalProfileDao;
      }
    }
  }

  @Override
  public HydrationLogDao hydrationLogDao() {
    if (_hydrationLogDao != null) {
      return _hydrationLogDao;
    } else {
      synchronized(this) {
        if(_hydrationLogDao == null) {
          _hydrationLogDao = new HydrationLogDao_Impl(this);
        }
        return _hydrationLogDao;
      }
    }
  }

  @Override
  public AlertDao alertDao() {
    if (_alertDao != null) {
      return _alertDao;
    } else {
      synchronized(this) {
        if(_alertDao == null) {
          _alertDao = new AlertDao_Impl(this);
        }
        return _alertDao;
      }
    }
  }

  @Override
  public HealthCheckInDao healthCheckInDao() {
    if (_healthCheckInDao != null) {
      return _healthCheckInDao;
    } else {
      synchronized(this) {
        if(_healthCheckInDao == null) {
          _healthCheckInDao = new HealthCheckInDao_Impl(this);
        }
        return _healthCheckInDao;
      }
    }
  }

  @Override
  public PairedGuardianDao pairedGuardianDao() {
    if (_pairedGuardianDao != null) {
      return _pairedGuardianDao;
    } else {
      synchronized(this) {
        if(_pairedGuardianDao == null) {
          _pairedGuardianDao = new PairedGuardianDao_Impl(this);
        }
        return _pairedGuardianDao;
      }
    }
  }
}
