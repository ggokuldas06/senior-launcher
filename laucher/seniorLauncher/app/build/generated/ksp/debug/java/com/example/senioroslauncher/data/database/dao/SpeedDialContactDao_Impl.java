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
import com.example.senioroslauncher.data.database.entity.SpeedDialContactEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class SpeedDialContactDao_Impl implements SpeedDialContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SpeedDialContactEntity> __insertionAdapterOfSpeedDialContactEntity;

  private final EntityDeletionOrUpdateAdapter<SpeedDialContactEntity> __deletionAdapterOfSpeedDialContactEntity;

  private final EntityDeletionOrUpdateAdapter<SpeedDialContactEntity> __updateAdapterOfSpeedDialContactEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAtPosition;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public SpeedDialContactDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSpeedDialContactEntity = new EntityInsertionAdapter<SpeedDialContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `speed_dial_contacts` (`id`,`name`,`phoneNumber`,`photoUri`,`position`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SpeedDialContactEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhoneNumber());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUri());
        }
        statement.bindLong(5, entity.getPosition());
      }
    };
    this.__deletionAdapterOfSpeedDialContactEntity = new EntityDeletionOrUpdateAdapter<SpeedDialContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `speed_dial_contacts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SpeedDialContactEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSpeedDialContactEntity = new EntityDeletionOrUpdateAdapter<SpeedDialContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `speed_dial_contacts` SET `id` = ?,`name` = ?,`phoneNumber` = ?,`photoUri` = ?,`position` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SpeedDialContactEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhoneNumber());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoUri());
        }
        statement.bindLong(5, entity.getPosition());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAtPosition = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM speed_dial_contacts WHERE position = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM speed_dial_contacts WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SpeedDialContactEntity contact,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSpeedDialContactEntity.insertAndReturnId(contact);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final SpeedDialContactEntity contact,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSpeedDialContactEntity.handle(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SpeedDialContactEntity contact,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSpeedDialContactEntity.handle(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAtPosition(final int position, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAtPosition.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, position);
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
          __preparedStmtOfDeleteAtPosition.release(_stmt);
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
  public Flow<List<SpeedDialContactEntity>> getAllSpeedDialContacts() {
    final String _sql = "SELECT * FROM speed_dial_contacts ORDER BY position ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"speed_dial_contacts"}, new Callable<List<SpeedDialContactEntity>>() {
      @Override
      @NonNull
      public List<SpeedDialContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final List<SpeedDialContactEntity> _result = new ArrayList<SpeedDialContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SpeedDialContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item = new SpeedDialContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpPhotoUri,_tmpPosition);
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
  public Object getAllSpeedDialContactsSync(
      final Continuation<? super List<SpeedDialContactEntity>> $completion) {
    final String _sql = "SELECT * FROM speed_dial_contacts ORDER BY position ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SpeedDialContactEntity>>() {
      @Override
      @NonNull
      public List<SpeedDialContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final List<SpeedDialContactEntity> _result = new ArrayList<SpeedDialContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SpeedDialContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item = new SpeedDialContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpPhotoUri,_tmpPosition);
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
  public Object getContactAtPosition(final int position,
      final Continuation<? super SpeedDialContactEntity> $completion) {
    final String _sql = "SELECT * FROM speed_dial_contacts WHERE position = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, position);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SpeedDialContactEntity>() {
      @Override
      @Nullable
      public SpeedDialContactEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final SpeedDialContactEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _result = new SpeedDialContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpPhotoUri,_tmpPosition);
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
  public Object getContactById(final long id,
      final Continuation<? super SpeedDialContactEntity> $completion) {
    final String _sql = "SELECT * FROM speed_dial_contacts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SpeedDialContactEntity>() {
      @Override
      @Nullable
      public SpeedDialContactEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final SpeedDialContactEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _result = new SpeedDialContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpPhotoUri,_tmpPosition);
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
