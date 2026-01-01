package com.example.senioroslauncher.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.senioroslauncher.data.database.Converters;
import com.example.senioroslauncher.data.database.entity.MedicalProfileEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MedicalProfileDao_Impl implements MedicalProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MedicalProfileEntity> __insertionAdapterOfMedicalProfileEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<MedicalProfileEntity> __updateAdapterOfMedicalProfileEntity;

  public MedicalProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicalProfileEntity = new EntityInsertionAdapter<MedicalProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medical_profile` (`id`,`bloodType`,`allergies`,`medicalConditions`,`emergencyNotes`,`doctorName`,`doctorPhone`,`insuranceInfo`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicalProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getBloodType());
        statement.bindString(3, entity.getAllergies());
        statement.bindString(4, entity.getMedicalConditions());
        statement.bindString(5, entity.getEmergencyNotes());
        statement.bindString(6, entity.getDoctorName());
        statement.bindString(7, entity.getDoctorPhone());
        statement.bindString(8, entity.getInsuranceInfo());
        final Long _tmp = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp);
        }
      }
    };
    this.__updateAdapterOfMedicalProfileEntity = new EntityDeletionOrUpdateAdapter<MedicalProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `medical_profile` SET `id` = ?,`bloodType` = ?,`allergies` = ?,`medicalConditions` = ?,`emergencyNotes` = ?,`doctorName` = ?,`doctorPhone` = ?,`insuranceInfo` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicalProfileEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getBloodType());
        statement.bindString(3, entity.getAllergies());
        statement.bindString(4, entity.getMedicalConditions());
        statement.bindString(5, entity.getEmergencyNotes());
        statement.bindString(6, entity.getDoctorName());
        statement.bindString(7, entity.getDoctorPhone());
        statement.bindString(8, entity.getInsuranceInfo());
        final Long _tmp = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp);
        }
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final MedicalProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedicalProfileEntity.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final MedicalProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMedicalProfileEntity.handle(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object saveProfile(final MedicalProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> MedicalProfileDao.DefaultImpls.saveProfile(MedicalProfileDao_Impl.this, profile, __cont), $completion);
  }

  @Override
  public Flow<MedicalProfileEntity> getProfile() {
    final String _sql = "SELECT * FROM medical_profile WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medical_profile"}, new Callable<MedicalProfileEntity>() {
      @Override
      @Nullable
      public MedicalProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfEmergencyNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyNotes");
          final int _cursorIndexOfDoctorName = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorName");
          final int _cursorIndexOfDoctorPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorPhone");
          final int _cursorIndexOfInsuranceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceInfo");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final MedicalProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBloodType;
            _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            final String _tmpAllergies;
            _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            final String _tmpMedicalConditions;
            _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            final String _tmpEmergencyNotes;
            _tmpEmergencyNotes = _cursor.getString(_cursorIndexOfEmergencyNotes);
            final String _tmpDoctorName;
            _tmpDoctorName = _cursor.getString(_cursorIndexOfDoctorName);
            final String _tmpDoctorPhone;
            _tmpDoctorPhone = _cursor.getString(_cursorIndexOfDoctorPhone);
            final String _tmpInsuranceInfo;
            _tmpInsuranceInfo = _cursor.getString(_cursorIndexOfInsuranceInfo);
            final Date _tmpUpdatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_1;
            }
            _result = new MedicalProfileEntity(_tmpId,_tmpBloodType,_tmpAllergies,_tmpMedicalConditions,_tmpEmergencyNotes,_tmpDoctorName,_tmpDoctorPhone,_tmpInsuranceInfo,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getProfileSync(final Continuation<? super MedicalProfileEntity> $completion) {
    final String _sql = "SELECT * FROM medical_profile WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MedicalProfileEntity>() {
      @Override
      @Nullable
      public MedicalProfileEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfEmergencyNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyNotes");
          final int _cursorIndexOfDoctorName = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorName");
          final int _cursorIndexOfDoctorPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorPhone");
          final int _cursorIndexOfInsuranceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceInfo");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final MedicalProfileEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBloodType;
            _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            final String _tmpAllergies;
            _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            final String _tmpMedicalConditions;
            _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            final String _tmpEmergencyNotes;
            _tmpEmergencyNotes = _cursor.getString(_cursorIndexOfEmergencyNotes);
            final String _tmpDoctorName;
            _tmpDoctorName = _cursor.getString(_cursorIndexOfDoctorName);
            final String _tmpDoctorPhone;
            _tmpDoctorPhone = _cursor.getString(_cursorIndexOfDoctorPhone);
            final String _tmpInsuranceInfo;
            _tmpInsuranceInfo = _cursor.getString(_cursorIndexOfInsuranceInfo);
            final Date _tmpUpdatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_1;
            }
            _result = new MedicalProfileEntity(_tmpId,_tmpBloodType,_tmpAllergies,_tmpMedicalConditions,_tmpEmergencyNotes,_tmpDoctorName,_tmpDoctorPhone,_tmpInsuranceInfo,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
