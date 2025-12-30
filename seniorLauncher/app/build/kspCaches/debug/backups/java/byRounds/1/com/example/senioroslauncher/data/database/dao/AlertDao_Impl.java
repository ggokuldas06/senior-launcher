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
import com.example.senioroslauncher.data.database.entity.AlertEntity;
import com.example.senioroslauncher.data.database.entity.AlertType;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
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
public final class AlertDao_Impl implements AlertDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AlertEntity> __insertionAdapterOfAlertEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<AlertEntity> __deletionAdapterOfAlertEntity;

  private final EntityDeletionOrUpdateAdapter<AlertEntity> __updateAdapterOfAlertEntity;

  private final SharedSQLiteStatement __preparedStmtOfResolveAlert;

  public AlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlertEntity = new EntityInsertionAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `alerts` (`id`,`type`,`triggeredAt`,`latitude`,`longitude`,`batteryLevel`,`resolved`,`resolvedAt`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.fromAlertType(entity.getType());
        statement.bindString(2, _tmp);
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getTriggeredAt());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp_1);
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLongitude());
        }
        if (entity.getBatteryLevel() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getBatteryLevel());
        }
        final int _tmp_2 = entity.getResolved() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getResolvedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
        statement.bindString(9, entity.getNotes());
      }
    };
    this.__deletionAdapterOfAlertEntity = new EntityDeletionOrUpdateAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `alerts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAlertEntity = new EntityDeletionOrUpdateAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `alerts` SET `id` = ?,`type` = ?,`triggeredAt` = ?,`latitude` = ?,`longitude` = ?,`batteryLevel` = ?,`resolved` = ?,`resolvedAt` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.fromAlertType(entity.getType());
        statement.bindString(2, _tmp);
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getTriggeredAt());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp_1);
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLongitude());
        }
        if (entity.getBatteryLevel() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getBatteryLevel());
        }
        final int _tmp_2 = entity.getResolved() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getResolvedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
        statement.bindString(9, entity.getNotes());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfResolveAlert = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alerts SET resolved = 1, resolvedAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AlertEntity alert, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAlertEntity.insertAndReturnId(alert);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAlertEntity.handle(alert);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAlertEntity.handle(alert);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object resolveAlert(final long id, final Date resolvedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResolveAlert.acquire();
        int _argIndex = 1;
        final Long _tmp = __converters.dateToTimestamp(resolvedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfResolveAlert.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AlertEntity>> getAllAlerts() {
    final String _sql = "SELECT * FROM alerts ORDER BY triggeredAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp);
            final Date _tmpTriggeredAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_2;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_3 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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
  public Flow<List<AlertEntity>> getRecentAlerts(final int limit) {
    final String _sql = "SELECT * FROM alerts ORDER BY triggeredAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp);
            final Date _tmpTriggeredAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_2;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_3 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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
  public Flow<List<AlertEntity>> getUnresolvedAlerts() {
    final String _sql = "SELECT * FROM alerts WHERE resolved = 0 ORDER BY triggeredAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp);
            final Date _tmpTriggeredAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_2;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_3 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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
  public Flow<List<AlertEntity>> getAlertsByType(final AlertType type) {
    final String _sql = "SELECT * FROM alerts WHERE type = ? ORDER BY triggeredAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromAlertType(type);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp_1);
            final Date _tmpTriggeredAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_3;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_4 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_5);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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
  public Flow<List<AlertEntity>> getAlertsBetweenDates(final Date startDate, final Date endDate) {
    final String _sql = "SELECT * FROM alerts WHERE triggeredAt BETWEEN ? AND ? ORDER BY triggeredAt DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alerts"}, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp_2);
            final Date _tmpTriggeredAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_4;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_5 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_6);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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
  public Object getAlertById(final long id, final Continuation<? super AlertEntity> $completion) {
    final String _sql = "SELECT * FROM alerts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AlertEntity>() {
      @Override
      @Nullable
      public AlertEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final AlertEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp);
            final Date _tmpTriggeredAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_2;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_3 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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

  @Override
  public Object hasRecentAlert(final AlertType type, final Date since,
      final Continuation<? super AlertEntity> $completion) {
    final String _sql = "SELECT * FROM alerts WHERE type = ? AND triggeredAt > ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromAlertType(type);
    _statement.bindString(_argIndex, _tmp);
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(since);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AlertEntity>() {
      @Override
      @Nullable
      public AlertEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final AlertEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp_2);
            final Date _tmpTriggeredAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_4;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_5 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_6);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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

  @Override
  public Object getAllAlertsSync(final Continuation<? super List<AlertEntity>> $completion) {
    final String _sql = "SELECT * FROM alerts ORDER BY triggeredAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp);
            final Date _tmpTriggeredAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_2;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_3 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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
  public Object getRecentAlertsSync(final int limit,
      final Continuation<? super List<AlertEntity>> $completion) {
    final String _sql = "SELECT * FROM alerts ORDER BY triggeredAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBatteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryLevel");
          final int _cursorIndexOfResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved");
          final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AlertType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toAlertType(_tmp);
            final Date _tmpTriggeredAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTriggeredAt = _tmp_2;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final Integer _tmpBatteryLevel;
            if (_cursor.isNull(_cursorIndexOfBatteryLevel)) {
              _tmpBatteryLevel = null;
            } else {
              _tmpBatteryLevel = _cursor.getInt(_cursorIndexOfBatteryLevel);
            }
            final boolean _tmpResolved;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfResolved);
            _tmpResolved = _tmp_3 != 0;
            final Date _tmpResolvedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfResolvedAt);
            }
            _tmpResolvedAt = __converters.fromTimestamp(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new AlertEntity(_tmpId,_tmpType,_tmpTriggeredAt,_tmpLatitude,_tmpLongitude,_tmpBatteryLevel,_tmpResolved,_tmpResolvedAt,_tmpNotes);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
