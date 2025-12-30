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
import com.example.senioroslauncher.data.database.entity.HydrationLogEntity;
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
public final class HydrationLogDao_Impl implements HydrationLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HydrationLogEntity> __insertionAdapterOfHydrationLogEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<HydrationLogEntity> __updateAdapterOfHydrationLogEntity;

  public HydrationLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHydrationLogEntity = new EntityInsertionAdapter<HydrationLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `hydration_logs` (`id`,`date`,`glassesCount`,`goal`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HydrationLogEntity entity) {
        statement.bindLong(1, entity.getId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        statement.bindLong(3, entity.getGlassesCount());
        statement.bindLong(4, entity.getGoal());
      }
    };
    this.__updateAdapterOfHydrationLogEntity = new EntityDeletionOrUpdateAdapter<HydrationLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `hydration_logs` SET `id` = ?,`date` = ?,`glassesCount` = ?,`goal` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HydrationLogEntity entity) {
        statement.bindLong(1, entity.getId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        statement.bindLong(3, entity.getGlassesCount());
        statement.bindLong(4, entity.getGoal());
        statement.bindLong(5, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final HydrationLogEntity log, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHydrationLogEntity.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final HydrationLogEntity log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHydrationLogEntity.handle(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementGlasses(final Date startOfDay, final Date endOfDay,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HydrationLogDao.DefaultImpls.incrementGlasses(HydrationLogDao_Impl.this, startOfDay, endOfDay, __cont), $completion);
  }

  @Override
  public Object decrementGlasses(final Date startOfDay, final Date endOfDay,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HydrationLogDao.DefaultImpls.decrementGlasses(HydrationLogDao_Impl.this, startOfDay, endOfDay, __cont), $completion);
  }

  @Override
  public Flow<HydrationLogEntity> getTodayLog(final Date startOfDay, final Date endOfDay) {
    final String _sql = "SELECT * FROM hydration_logs WHERE date BETWEEN ? AND ? LIMIT 1";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"hydration_logs"}, new Callable<HydrationLogEntity>() {
      @Override
      @Nullable
      public HydrationLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfGlassesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "glassesCount");
          final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
          final HydrationLogEntity _result;
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
            final int _tmpGlassesCount;
            _tmpGlassesCount = _cursor.getInt(_cursorIndexOfGlassesCount);
            final int _tmpGoal;
            _tmpGoal = _cursor.getInt(_cursorIndexOfGoal);
            _result = new HydrationLogEntity(_tmpId,_tmpDate,_tmpGlassesCount,_tmpGoal);
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
  public Object getTodayLogSync(final Date startOfDay, final Date endOfDay,
      final Continuation<? super HydrationLogEntity> $completion) {
    final String _sql = "SELECT * FROM hydration_logs WHERE date BETWEEN ? AND ? LIMIT 1";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HydrationLogEntity>() {
      @Override
      @Nullable
      public HydrationLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfGlassesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "glassesCount");
          final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
          final HydrationLogEntity _result;
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
            final int _tmpGlassesCount;
            _tmpGlassesCount = _cursor.getInt(_cursorIndexOfGlassesCount);
            final int _tmpGoal;
            _tmpGoal = _cursor.getInt(_cursorIndexOfGoal);
            _result = new HydrationLogEntity(_tmpId,_tmpDate,_tmpGlassesCount,_tmpGoal);
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
  public Flow<List<HydrationLogEntity>> getRecentLogs(final int limit) {
    final String _sql = "SELECT * FROM hydration_logs ORDER BY date DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"hydration_logs"}, new Callable<List<HydrationLogEntity>>() {
      @Override
      @NonNull
      public List<HydrationLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfGlassesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "glassesCount");
          final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
          final List<HydrationLogEntity> _result = new ArrayList<HydrationLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HydrationLogEntity _item;
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
            final int _tmpGlassesCount;
            _tmpGlassesCount = _cursor.getInt(_cursorIndexOfGlassesCount);
            final int _tmpGoal;
            _tmpGoal = _cursor.getInt(_cursorIndexOfGoal);
            _item = new HydrationLogEntity(_tmpId,_tmpDate,_tmpGlassesCount,_tmpGoal);
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
