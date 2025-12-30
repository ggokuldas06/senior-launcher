package com.example.senioroslauncher.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
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
import com.example.senioroslauncher.data.database.entity.MedicationAction;
import com.example.senioroslauncher.data.database.entity.MedicationLogEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
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
public final class MedicationLogDao_Impl implements MedicationLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MedicationLogEntity> __insertionAdapterOfMedicationLogEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<MedicationLogEntity> __deletionAdapterOfMedicationLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllForMedication;

  public MedicationLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicationLogEntity = new EntityInsertionAdapter<MedicationLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medication_logs` (`id`,`medicationId`,`scheduledTime`,`actionTime`,`action`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMedicationId());
        final Long _tmp = __converters.dateToTimestamp(entity.getScheduledTime());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getActionTime());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        final String _tmp_2 = __converters.fromMedicationAction(entity.getAction());
        statement.bindString(5, _tmp_2);
        statement.bindString(6, entity.getNotes());
      }
    };
    this.__deletionAdapterOfMedicationLogEntity = new EntityDeletionOrUpdateAdapter<MedicationLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `medication_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicationLogEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllForMedication = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM medication_logs WHERE medicationId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MedicationLogEntity log,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMedicationLogEntity.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final MedicationLogEntity log,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMedicationLogEntity.handle(log);
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
  public Flow<List<MedicationLogEntity>> getLogsForMedication(final long medicationId) {
    final String _sql = "SELECT * FROM medication_logs WHERE medicationId = ? ORDER BY actionTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, medicationId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medication_logs"}, new Callable<List<MedicationLogEntity>>() {
      @Override
      @NonNull
      public List<MedicationLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfActionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "actionTime");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicationLogEntity> _result = new ArrayList<MedicationLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final Date _tmpScheduledTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfScheduledTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfScheduledTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpScheduledTime = _tmp_1;
            }
            final Date _tmpActionTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActionTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfActionTime);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpActionTime = _tmp_3;
            }
            final MedicationAction _tmpAction;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfAction);
            _tmpAction = __converters.toMedicationAction(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new MedicationLogEntity(_tmpId,_tmpMedicationId,_tmpScheduledTime,_tmpActionTime,_tmpAction,_tmpNotes);
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
  public Flow<List<MedicationLogEntity>> getLogsBetweenDates(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM medication_logs WHERE actionTime BETWEEN ? AND ? ORDER BY actionTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medication_logs"}, new Callable<List<MedicationLogEntity>>() {
      @Override
      @NonNull
      public List<MedicationLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfActionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "actionTime");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicationLogEntity> _result = new ArrayList<MedicationLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final Date _tmpScheduledTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfScheduledTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfScheduledTime);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpScheduledTime = _tmp_3;
            }
            final Date _tmpActionTime;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfActionTime)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfActionTime);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpActionTime = _tmp_5;
            }
            final MedicationAction _tmpAction;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfAction);
            _tmpAction = __converters.toMedicationAction(_tmp_6);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new MedicationLogEntity(_tmpId,_tmpMedicationId,_tmpScheduledTime,_tmpActionTime,_tmpAction,_tmpNotes);
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
  public Flow<List<MedicationLogEntity>> getRecentLogs(final int limit) {
    final String _sql = "SELECT * FROM medication_logs ORDER BY actionTime DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medication_logs"}, new Callable<List<MedicationLogEntity>>() {
      @Override
      @NonNull
      public List<MedicationLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMedicationId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicationId");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfActionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "actionTime");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicationLogEntity> _result = new ArrayList<MedicationLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicationLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMedicationId;
            _tmpMedicationId = _cursor.getLong(_cursorIndexOfMedicationId);
            final Date _tmpScheduledTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfScheduledTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfScheduledTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpScheduledTime = _tmp_1;
            }
            final Date _tmpActionTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActionTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfActionTime);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpActionTime = _tmp_3;
            }
            final MedicationAction _tmpAction;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfAction);
            _tmpAction = __converters.toMedicationAction(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new MedicationLogEntity(_tmpId,_tmpMedicationId,_tmpScheduledTime,_tmpActionTime,_tmpAction,_tmpNotes);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
