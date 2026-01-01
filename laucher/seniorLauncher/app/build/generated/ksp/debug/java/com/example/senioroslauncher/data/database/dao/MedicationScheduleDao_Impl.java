package com.example.senioroslauncher.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.senioroslauncher.data.database.Converters;
import com.example.senioroslauncher.data.database.entity.MedicationScheduleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MedicationScheduleDao_Impl implements MedicationScheduleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MedicationScheduleEntity> __insertionAdapterOfMedicationScheduleEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<MedicationScheduleEntity> __deletionAdapterOfMedicationScheduleEntity;

  private final EntityDeletionOrUpdateAdapter<MedicationScheduleEntity> __updateAdapterOfMedicationScheduleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllForMedication;

  public MedicationScheduleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicationScheduleEntity = new EntityInsertionAdapter<MedicationScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medication_schedules` (`id`,`medicationId`,`hour`,`minute`,`daysOfWeek`,`isEnabled`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationScheduleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMedicationId());
        statement.bindLong(3, entity.getHour());
        statement.bindLong(4, entity.getMinute());
        final String _tmp = __converters.toIntList(entity.getDaysOfWeek());
        statement.bindString(5, _tmp);
        final int _tmp_1 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
      }
    };
    this.__deletionAdapterOfMedicationScheduleEntity = new EntityDeletionOrUpdateAdapter<MedicationScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `medication_schedules` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationScheduleEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMedicationScheduleEntity = new EntityDeletionOrUpdateAdapter<MedicationScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `medication_schedules` SET `id` = ?,`medicationId` = ?,`hour` = ?,`minute` = ?,`daysOfWeek` = ?,`isEnabled` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationScheduleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMedicationId());
        statement.bindLong(3, entity.getHour());
        statement.bindLong(4, entity.getMinute());
        final String _tmp = __converters.toIntList(entity.getDaysOfWeek());
        statement.bindString(5, _tmp);
        final int _tmp_1 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllForMedication = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM medication_schedules WHERE medicationId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MedicationScheduleEntity schedule,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMedicationScheduleEntity.insertAndReturnId(schedule);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<MedicationScheduleEntity> schedules,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedicationScheduleEntity.insert(schedules);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final MedicationScheduleEntity schedule,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMedicationScheduleEntity.handle(schedule);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final MedicationScheduleEntity schedule,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMedicationScheduleEntity.handle(schedule);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllForMedication(final long medicationId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllForMedication.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, medicationId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllForMedication.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MedicationScheduleEntity>> getSchedulesForMedication(final long medicationId) {
    final String _sql = "SELECT * FROM medication_schedules WHERE medicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, medicationId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medication_schedules"}, new Callable<List<MedicationScheduleEntity>>() {
      @Override
      @NonNull
      public List<MedicationScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDaysOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "daysOfWeek");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final List<MedicationScheduleEntity> _result = new ArrayList<MedicationScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationScheduleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final List<Integer> _tmpDaysOfWeek;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDaysOfWeek);
            _tmpDaysOfWeek = __converters.fromIntList(_tmp);
            final boolean _tmpIsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_1 != 0;
            _item = new MedicationScheduleEntity(_tmpId,_tmpMedicationId,_tmpHour,_tmpMinute,_tmpDaysOfWeek,_tmpIsEnabled);
            _result.add(_item);
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
  public Object getSchedulesForMedicationSync(final long medicationId,
      final Continuation<? super List<MedicationScheduleEntity>> $completion) {
    final String _sql = "SELECT * FROM medication_schedules WHERE medicationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, medicationId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MedicationScheduleEntity>>() {
      @Override
      @NonNull
      public List<MedicationScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDaysOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "daysOfWeek");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final List<MedicationScheduleEntity> _result = new ArrayList<MedicationScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationScheduleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final List<Integer> _tmpDaysOfWeek;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDaysOfWeek);
            _tmpDaysOfWeek = __converters.fromIntList(_tmp);
            final boolean _tmpIsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_1 != 0;
            _item = new MedicationScheduleEntity(_tmpId,_tmpMedicationId,_tmpHour,_tmpMinute,_tmpDaysOfWeek,_tmpIsEnabled);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllEnabledSchedules(
      final Continuation<? super List<MedicationScheduleEntity>> $completion) {
    final String _sql = "SELECT * FROM medication_schedules WHERE isEnabled = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MedicationScheduleEntity>>() {
      @Override
      @NonNull
      public List<MedicationScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDaysOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "daysOfWeek");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final List<MedicationScheduleEntity> _result = new ArrayList<MedicationScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationScheduleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final List<Integer> _tmpDaysOfWeek;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDaysOfWeek);
            _tmpDaysOfWeek = __converters.fromIntList(_tmp);
            final boolean _tmpIsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_1 != 0;
            _item = new MedicationScheduleEntity(_tmpId,_tmpMedicationId,_tmpHour,_tmpMinute,_tmpDaysOfWeek,_tmpIsEnabled);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getScheduleById(final long id,
      final Continuation<? super MedicationScheduleEntity> $completion) {
    final String _sql = "SELECT * FROM medication_schedules WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MedicationScheduleEntity>() {
      @Override
      @Nullable
      public MedicationScheduleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDaysOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "daysOfWeek");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final MedicationScheduleEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final List<Integer> _tmpDaysOfWeek;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDaysOfWeek);
            _tmpDaysOfWeek = __converters.fromIntList(_tmp);
            final boolean _tmpIsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_1 != 0;
            _result = new MedicationScheduleEntity(_tmpId,_tmpMedicationId,_tmpHour,_tmpMinute,_tmpDaysOfWeek,_tmpIsEnabled);
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
