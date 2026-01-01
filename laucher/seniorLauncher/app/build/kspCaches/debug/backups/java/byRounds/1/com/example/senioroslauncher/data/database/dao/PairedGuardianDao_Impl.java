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
import com.example.senioroslauncher.data.database.entity.PairedGuardianEntity;
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
public final class PairedGuardianDao_Impl implements PairedGuardianDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PairedGuardianEntity> __insertionAdapterOfPairedGuardianEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<PairedGuardianEntity> __deletionAdapterOfPairedGuardianEntity;

  private final EntityDeletionOrUpdateAdapter<PairedGuardianEntity> __updateAdapterOfPairedGuardianEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PairedGuardianDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPairedGuardianEntity = new EntityInsertionAdapter<PairedGuardianEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `paired_guardians` (`guardianId`,`guardianName`,`pairedAt`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PairedGuardianEntity entity) {
        statement.bindString(1, entity.getGuardianId());
        statement.bindString(2, entity.getGuardianName());
        final Long _tmp = __converters.dateToTimestamp(entity.getPairedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
      }
    };
    this.__deletionAdapterOfPairedGuardianEntity = new EntityDeletionOrUpdateAdapter<PairedGuardianEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `paired_guardians` WHERE `guardianId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PairedGuardianEntity entity) {
        statement.bindString(1, entity.getGuardianId());
      }
    };
    this.__updateAdapterOfPairedGuardianEntity = new EntityDeletionOrUpdateAdapter<PairedGuardianEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `paired_guardians` SET `guardianId` = ?,`guardianName` = ?,`pairedAt` = ? WHERE `guardianId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PairedGuardianEntity entity) {
        statement.bindString(1, entity.getGuardianId());
        statement.bindString(2, entity.getGuardianName());
        final Long _tmp = __converters.dateToTimestamp(entity.getPairedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindString(4, entity.getGuardianId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM paired_guardians WHERE guardianId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM paired_guardians";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PairedGuardianEntity guardian,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPairedGuardianEntity.insert(guardian);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PairedGuardianEntity guardian,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPairedGuardianEntity.handle(guardian);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PairedGuardianEntity guardian,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPairedGuardianEntity.handle(guardian);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String guardianId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, guardianId);
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
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
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
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PairedGuardianEntity>> getAllGuardians() {
    final String _sql = "SELECT * FROM paired_guardians ORDER BY pairedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"paired_guardians"}, new Callable<List<PairedGuardianEntity>>() {
      @Override
      @NonNull
      public List<PairedGuardianEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfPairedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedAt");
          final List<PairedGuardianEntity> _result = new ArrayList<PairedGuardianEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PairedGuardianEntity _item;
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final Date _tmpPairedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPairedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPairedAt);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPairedAt = _tmp_1;
            }
            _item = new PairedGuardianEntity(_tmpGuardianId,_tmpGuardianName,_tmpPairedAt);
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
  public Object getAllGuardiansSync(
      final Continuation<? super List<PairedGuardianEntity>> $completion) {
    final String _sql = "SELECT * FROM paired_guardians ORDER BY pairedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PairedGuardianEntity>>() {
      @Override
      @NonNull
      public List<PairedGuardianEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfPairedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedAt");
          final List<PairedGuardianEntity> _result = new ArrayList<PairedGuardianEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PairedGuardianEntity _item;
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final Date _tmpPairedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPairedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPairedAt);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPairedAt = _tmp_1;
            }
            _item = new PairedGuardianEntity(_tmpGuardianId,_tmpGuardianName,_tmpPairedAt);
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
  public Object getGuardianById(final String guardianId,
      final Continuation<? super PairedGuardianEntity> $completion) {
    final String _sql = "SELECT * FROM paired_guardians WHERE guardianId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, guardianId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PairedGuardianEntity>() {
      @Override
      @Nullable
      public PairedGuardianEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfGuardianName = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianName");
          final int _cursorIndexOfPairedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedAt");
          final PairedGuardianEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpGuardianId;
            _tmpGuardianId = _cursor.getString(_cursorIndexOfGuardianId);
            final String _tmpGuardianName;
            _tmpGuardianName = _cursor.getString(_cursorIndexOfGuardianName);
            final Date _tmpPairedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPairedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPairedAt);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPairedAt = _tmp_1;
            }
            _result = new PairedGuardianEntity(_tmpGuardianId,_tmpGuardianName,_tmpPairedAt);
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
  public Flow<Integer> getGuardianCount() {
    final String _sql = "SELECT COUNT(*) FROM paired_guardians";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"paired_guardians"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getGuardianCountSync(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM paired_guardians";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
