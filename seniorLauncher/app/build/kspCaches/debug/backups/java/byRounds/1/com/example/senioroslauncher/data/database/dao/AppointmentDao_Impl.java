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
import com.example.senioroslauncher.data.database.entity.AppointmentEntity;
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
public final class AppointmentDao_Impl implements AppointmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AppointmentEntity> __insertionAdapterOfAppointmentEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<AppointmentEntity> __deletionAdapterOfAppointmentEntity;

  private final EntityDeletionOrUpdateAdapter<AppointmentEntity> __updateAdapterOfAppointmentEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public AppointmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppointmentEntity = new EntityInsertionAdapter<AppointmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `appointments` (`id`,`title`,`dateTime`,`location`,`description`,`reminderMinutesBefore`,`isReminderEnabled`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AppointmentEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        final Long _tmp = __converters.dateToTimestamp(entity.getDateTime());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindString(4, entity.getLocation());
        statement.bindString(5, entity.getDescription());
        statement.bindLong(6, entity.getReminderMinutesBefore());
        final int _tmp_1 = entity.isReminderEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfAppointmentEntity = new EntityDeletionOrUpdateAdapter<AppointmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `appointments` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AppointmentEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAppointmentEntity = new EntityDeletionOrUpdateAdapter<AppointmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `appointments` SET `id` = ?,`title` = ?,`dateTime` = ?,`location` = ?,`description` = ?,`reminderMinutesBefore` = ?,`isReminderEnabled` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AppointmentEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        final Long _tmp = __converters.dateToTimestamp(entity.getDateTime());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindString(4, entity.getLocation());
        statement.bindString(5, entity.getDescription());
        statement.bindLong(6, entity.getReminderMinutesBefore());
        final int _tmp_1 = entity.isReminderEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM appointments WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AppointmentEntity appointment,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAppointmentEntity.insertAndReturnId(appointment);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final AppointmentEntity appointment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAppointmentEntity.handle(appointment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final AppointmentEntity appointment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAppointmentEntity.handle(appointment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AppointmentEntity>> getAllAppointments() {
    final String _sql = "SELECT * FROM appointments ORDER BY dateTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"appointments"}, new Callable<List<AppointmentEntity>>() {
      @Override
      @NonNull
      public List<AppointmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTime");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfIsReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AppointmentEntity> _result = new ArrayList<AppointmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppointmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Date _tmpDateTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDateTime = _tmp_1;
            }
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final boolean _tmpIsReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsReminderEnabled);
            _tmpIsReminderEnabled = _tmp_2 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            _item = new AppointmentEntity(_tmpId,_tmpTitle,_tmpDateTime,_tmpLocation,_tmpDescription,_tmpReminderMinutesBefore,_tmpIsReminderEnabled,_tmpCreatedAt);
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
  public Flow<List<AppointmentEntity>> getUpcomingAppointments(final Date date) {
    final String _sql = "SELECT * FROM appointments WHERE dateTime >= ? ORDER BY dateTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"appointments"}, new Callable<List<AppointmentEntity>>() {
      @Override
      @NonNull
      public List<AppointmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTime");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfIsReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AppointmentEntity> _result = new ArrayList<AppointmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppointmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Date _tmpDateTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDateTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDateTime);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDateTime = _tmp_2;
            }
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final boolean _tmpIsReminderEnabled;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsReminderEnabled);
            _tmpIsReminderEnabled = _tmp_3 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            _item = new AppointmentEntity(_tmpId,_tmpTitle,_tmpDateTime,_tmpLocation,_tmpDescription,_tmpReminderMinutesBefore,_tmpIsReminderEnabled,_tmpCreatedAt);
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
  public Flow<List<AppointmentEntity>> getAppointmentsBetweenDates(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM appointments WHERE dateTime BETWEEN ? AND ? ORDER BY dateTime ASC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"appointments"}, new Callable<List<AppointmentEntity>>() {
      @Override
      @NonNull
      public List<AppointmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTime");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfIsReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AppointmentEntity> _result = new ArrayList<AppointmentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppointmentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Date _tmpDateTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDateTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDateTime);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDateTime = _tmp_3;
            }
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final boolean _tmpIsReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsReminderEnabled);
            _tmpIsReminderEnabled = _tmp_4 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_6 = __converters.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_6;
            }
            _item = new AppointmentEntity(_tmpId,_tmpTitle,_tmpDateTime,_tmpLocation,_tmpDescription,_tmpReminderMinutesBefore,_tmpIsReminderEnabled,_tmpCreatedAt);
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
  public Object getAppointmentById(final long id,
      final Continuation<? super AppointmentEntity> $completion) {
    final String _sql = "SELECT * FROM appointments WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AppointmentEntity>() {
      @Override
      @Nullable
      public AppointmentEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTime");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfIsReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isReminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final AppointmentEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Date _tmpDateTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDateTime = _tmp_1;
            }
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final boolean _tmpIsReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsReminderEnabled);
            _tmpIsReminderEnabled = _tmp_2 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            _result = new AppointmentEntity(_tmpId,_tmpTitle,_tmpDateTime,_tmpLocation,_tmpDescription,_tmpReminderMinutesBefore,_tmpIsReminderEnabled,_tmpCreatedAt);
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
