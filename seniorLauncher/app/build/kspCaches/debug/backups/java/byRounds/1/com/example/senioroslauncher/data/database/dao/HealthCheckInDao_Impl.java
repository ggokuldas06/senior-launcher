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
import com.example.senioroslauncher.data.database.entity.HealthCheckInEntity;
import java.lang.Class;
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
public final class HealthCheckInDao_Impl implements HealthCheckInDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HealthCheckInEntity> __insertionAdapterOfHealthCheckInEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<HealthCheckInEntity> __deletionAdapterOfHealthCheckInEntity;

  private final EntityDeletionOrUpdateAdapter<HealthCheckInEntity> __updateAdapterOfHealthCheckInEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public HealthCheckInDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHealthCheckInEntity = new EntityInsertionAdapter<HealthCheckInEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `health_checkins` (`id`,`date`,`mood`,`painLevel`,`sleepQuality`,`symptoms`,`notes`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HealthCheckInEntity entity) {
        statement.bindLong(1, entity.getId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        if (entity.getMood() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getMood());
        }
        if (entity.getPainLevel() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getPainLevel());
        }
        if (entity.getSleepQuality() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSleepQuality());
        }
        final String _tmp_1 = __converters.toStringList(entity.getSymptoms());
        statement.bindString(6, _tmp_1);
        statement.bindString(7, entity.getNotes());
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfHealthCheckInEntity = new EntityDeletionOrUpdateAdapter<HealthCheckInEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `health_checkins` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HealthCheckInEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfHealthCheckInEntity = new EntityDeletionOrUpdateAdapter<HealthCheckInEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `health_checkins` SET `id` = ?,`date` = ?,`mood` = ?,`painLevel` = ?,`sleepQuality` = ?,`symptoms` = ?,`notes` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HealthCheckInEntity entity) {
        statement.bindLong(1, entity.getId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        if (entity.getMood() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getMood());
        }
        if (entity.getPainLevel() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getPainLevel());
        }
        if (entity.getSleepQuality() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSleepQuality());
        }
        final String _tmp_1 = __converters.toStringList(entity.getSymptoms());
        statement.bindString(6, _tmp_1);
        statement.bindString(7, entity.getNotes());
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
        final String _query = "DELETE FROM health_checkins WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final HealthCheckInEntity checkIn,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHealthCheckInEntity.insertAndReturnId(checkIn);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final HealthCheckInEntity checkIn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHealthCheckInEntity.handle(checkIn);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final HealthCheckInEntity checkIn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHealthCheckInEntity.handle(checkIn);
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
  public Flow<List<HealthCheckInEntity>> getAllCheckIns() {
    final String _sql = "SELECT * FROM health_checkins ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"health_checkins"}, new Callable<List<HealthCheckInEntity>>() {
      @Override
      @NonNull
      public List<HealthCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HealthCheckInEntity> _result = new ArrayList<HealthCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HealthCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
  public Flow<List<HealthCheckInEntity>> getRecentCheckIns(final int limit) {
    final String _sql = "SELECT * FROM health_checkins ORDER BY date DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"health_checkins"}, new Callable<List<HealthCheckInEntity>>() {
      @Override
      @NonNull
      public List<HealthCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HealthCheckInEntity> _result = new ArrayList<HealthCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HealthCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
  public Flow<List<HealthCheckInEntity>> getCheckInsBetweenDates(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM health_checkins WHERE date BETWEEN ? AND ? ORDER BY date DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"health_checkins"}, new Callable<List<HealthCheckInEntity>>() {
      @Override
      @NonNull
      public List<HealthCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HealthCheckInEntity> _result = new ArrayList<HealthCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HealthCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_3;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
  public Object getCheckInById(final long id,
      final Continuation<? super HealthCheckInEntity> $completion) {
    final String _sql = "SELECT * FROM health_checkins WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HealthCheckInEntity>() {
      @Override
      @Nullable
      public HealthCheckInEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final HealthCheckInEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _result = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
  public Object getCheckInForDate(final Date startOfDay, final Date endOfDay,
      final Continuation<? super HealthCheckInEntity> $completion) {
    final String _sql = "SELECT * FROM health_checkins WHERE date >= ? AND date < ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(startOfDay);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(endOfDay);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HealthCheckInEntity>() {
      @Override
      @Nullable
      public HealthCheckInEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final HealthCheckInEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_3;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_4);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _result = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
  public Object getAllCheckInsSync(
      final Continuation<? super List<HealthCheckInEntity>> $completion) {
    final String _sql = "SELECT * FROM health_checkins ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HealthCheckInEntity>>() {
      @Override
      @NonNull
      public List<HealthCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HealthCheckInEntity> _result = new ArrayList<HealthCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HealthCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
  public Object getRecentCheckInsSync(final int limit,
      final Continuation<? super List<HealthCheckInEntity>> $completion) {
    final String _sql = "SELECT * FROM health_checkins ORDER BY date DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HealthCheckInEntity>>() {
      @Override
      @NonNull
      public List<HealthCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final int _cursorIndexOfPainLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "painLevel");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfSymptoms = CursorUtil.getColumnIndexOrThrow(_cursor, "symptoms");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HealthCheckInEntity> _result = new ArrayList<HealthCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HealthCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            final Integer _tmpPainLevel;
            if (_cursor.isNull(_cursorIndexOfPainLevel)) {
              _tmpPainLevel = null;
            } else {
              _tmpPainLevel = _cursor.getInt(_cursorIndexOfPainLevel);
            }
            final Integer _tmpSleepQuality;
            if (_cursor.isNull(_cursorIndexOfSleepQuality)) {
              _tmpSleepQuality = null;
            } else {
              _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            }
            final List<String> _tmpSymptoms;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfSymptoms);
            _tmpSymptoms = __converters.fromStringList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new HealthCheckInEntity(_tmpId,_tmpDate,_tmpMood,_tmpPainLevel,_tmpSleepQuality,_tmpSymptoms,_tmpNotes,_tmpCreatedAt);
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
